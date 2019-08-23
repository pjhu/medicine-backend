package com.pjhu.medicine.identity.filter;

import com.pjhu.medicine.common.cache.RedisNamespace;
import com.pjhu.medicine.identity.domain.model.UserTokenRepository;
import com.pjhu.medicine.common.cache.UserMeta;
import com.pjhu.medicine.identity.utils.AuthenticationUtil;
import com.pjhu.medicine.identity.utils.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ExternalUserTokenFilter extends AbstractAuthenticationProcessingFilter {

    private final UserTokenRepository userTokenRepository;

    public ExternalUserTokenFilter(RequestMatcher requiresAuthenticationRequestMatcher,
                                   UserTokenRepository userTokenRepository) {
        super(requiresAuthenticationRequestMatcher);
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }
        String requestHeader = request.getHeader(AuthenticationUtil.AUTHORIZATION_HEADER);
        if (StringUtils.isBlank(requestHeader)) {
            chain.doFilter(request, response);
            return;
        }
        String tokenUuid = AuthenticationUtil.tokenExtract(requestHeader, TokenType.APP);
        UserMeta userMeta = userTokenRepository.getBy(RedisNamespace.USER_NAME_SPACE, tokenUuid);

        if (userMeta != null) {
            userTokenRepository.refresh(RedisNamespace.USER_NAME_SPACE, tokenUuid);
            Authentication authentication = AuthenticationUtil.create(userMeta.getUsername(), userMeta.getRole());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(req, res);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        return null;
    }
}
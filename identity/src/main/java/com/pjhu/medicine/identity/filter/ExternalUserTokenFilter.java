package com.pjhu.medicine.identity.filter;

import com.pjhu.medicine.common.cache.ExternalUserMeta;
import com.pjhu.medicine.common.cache.RedisNamespace;
import com.pjhu.medicine.identity.domain.model.ExternalUserTokenRepository;
import com.pjhu.medicine.identity.utils.AuthenticationUtil;
import com.pjhu.medicine.identity.utils.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ExternalUserTokenFilter extends AbstractAuthenticationProcessingFilter {

    private final ExternalUserTokenRepository externalUserTokenRepository;

    public ExternalUserTokenFilter(RequestMatcher requiresAuthenticationRequestMatcher,
                                   ExternalUserTokenRepository externalUserTokenRepository) {
        super(requiresAuthenticationRequestMatcher);
        this.externalUserTokenRepository = externalUserTokenRepository;
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
        String tokenUuid = AuthenticationUtil.tokenExtract(requestHeader, TokenType.USER);
        ExternalUserMeta externalUserMeta = externalUserTokenRepository.getBy(RedisNamespace.USER_NAME_SPACE, tokenUuid);

        if (externalUserMeta != null) {
            externalUserTokenRepository.refresh(RedisNamespace.USER_NAME_SPACE, tokenUuid);
            Authentication authentication = AuthenticationUtil.createExternalUser(externalUserMeta.getUsername());
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

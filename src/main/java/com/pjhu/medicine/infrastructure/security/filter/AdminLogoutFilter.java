package com.pjhu.medicine.infrastructure.security.filter;

import com.pjhu.medicine.domain.auth.UserTokenRepository;
import com.pjhu.medicine.infrastructure.cache.RedisNamespace;
import com.pjhu.medicine.infrastructure.cache.UserMeta;
import com.pjhu.medicine.infrastructure.security.common.AuthenticationUtil;
import com.pjhu.medicine.infrastructure.security.common.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AdminLogoutFilter extends AbstractAuthenticationProcessingFilter {

    private final UserTokenRepository userTokenRepository;

    public AdminLogoutFilter(String defaultFilterProcessesUrl,
                             UserTokenRepository userTokenRepository) {
        super(defaultFilterProcessesUrl);
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String requestHeader = request.getHeader(AuthenticationUtil.AUTHORIZATION_HEADER);
        if (StringUtils.isNoneBlank(requestHeader)) {
            String tokenUuid = AuthenticationUtil.tokenExtract(requestHeader, TokenType.ADMIN);
            UserMeta userMeta = userTokenRepository.getBy(RedisNamespace.ADMIN_NAME_SPACE, tokenUuid);
            if (userMeta !=  null) {
                userTokenRepository.delete(RedisNamespace.ADMIN_NAME_SPACE, tokenUuid);
                return AuthenticationUtil.create(userMeta.getUsername(), userMeta.getRole());
            }
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return null;
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return null;
    }
}

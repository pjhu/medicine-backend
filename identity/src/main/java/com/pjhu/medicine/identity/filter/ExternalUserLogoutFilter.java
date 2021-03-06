package com.pjhu.medicine.identity.filter;

import com.pjhu.medicine.common.cache.ExternalUserMeta;
import com.pjhu.medicine.identity.domain.model.ExternalUserTokenRepository;
import com.pjhu.medicine.common.cache.RedisNamespace;
import com.pjhu.medicine.identity.utils.AuthenticationUtil;
import com.pjhu.medicine.identity.utils.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ExternalUserLogoutFilter extends AbstractAuthenticationProcessingFilter {

    private final ExternalUserTokenRepository externalUserTokenRepository;

    public ExternalUserLogoutFilter(String defaultFilterProcessesUrl,
                                    ExternalUserTokenRepository externalUserTokenRepository) {
        super(defaultFilterProcessesUrl);
        this.externalUserTokenRepository = externalUserTokenRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String requestHeader = request.getHeader(AuthenticationUtil.AUTHORIZATION_HEADER);
        if (StringUtils.isNoneBlank(requestHeader)) {
            String tokenUuid = AuthenticationUtil.tokenExtract(requestHeader, TokenType.USER);
            ExternalUserMeta externalUserMeta = externalUserTokenRepository.getBy(RedisNamespace.USER_NAME_SPACE, tokenUuid);
            if (externalUserMeta !=  null) {
                externalUserTokenRepository.delete(RedisNamespace.USER_NAME_SPACE, tokenUuid);
                return AuthenticationUtil.createExternalUser(externalUserMeta.getUsername());
            }
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return null;
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return null;
    }
}

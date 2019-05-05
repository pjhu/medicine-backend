package com.pjhu.medicine.infrastructure.security.filter;

import com.pjhu.medicine.domain.user.UserStatus;
import com.pjhu.medicine.domain.user.external.ExternalUser;
import com.pjhu.medicine.domain.user.external.ExternalUserRepository;
import com.pjhu.medicine.infrastructure.common.SuppressObjectMapper;
import com.pjhu.medicine.infrastructure.security.common.SignInRequest;
import com.pjhu.medicine.infrastructure.security.common.AuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ExternalUserJdbcProviderFilter extends AbstractAuthenticationProcessingFilter {

    private final ExternalUserRepository externalUserRepository;
    private final SuppressObjectMapper suppressObjectMapper;

    public ExternalUserJdbcProviderFilter(String defaultFilterProcessesUrl,
                                          ExternalUserRepository externalUserRepository,
                                          SuppressObjectMapper suppressObjectMapper) {
        super(defaultFilterProcessesUrl);
        this.externalUserRepository = externalUserRepository;
        this.suppressObjectMapper = suppressObjectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        String collect = IOUtils.toString(request.getReader());
        SignInRequest signInRequest =
                suppressObjectMapper.readValue(collect, SignInRequest.class);
        ExternalUser externalUser = externalUserRepository
                .findByUsernameAndActiveNot(signInRequest.getUsername(), UserStatus.DISABLE);
        if (externalUser != null) {
            return AuthenticationUtil.create(signInRequest.getUsername(), externalUser.getRole().name());
        }
        return null;
    }
}

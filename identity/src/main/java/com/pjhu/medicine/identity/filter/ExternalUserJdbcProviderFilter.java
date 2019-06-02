package com.pjhu.medicine.identity.filter;

import com.pjhu.medicine.identity.domain.model.UserStatus;
import com.pjhu.medicine.identity.domain.model.external.ExternalUser;
import com.pjhu.medicine.identity.domain.model.external.ExternalUserRepository;
import com.pjhu.medicine.common.utils.SuppressObjectMapper;
import com.pjhu.medicine.identity.adapter.SignInRequest;
import com.pjhu.medicine.identity.utils.AuthenticationUtil;
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

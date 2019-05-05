package com.pjhu.medicine.infrastructure.security.filter;

import com.pjhu.medicine.domain.user.Role;
import com.pjhu.medicine.infrastructure.common.SuppressObjectMapper;
import com.pjhu.medicine.infrastructure.security.common.LdapClient;
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
public class AdminLdapProviderFilter extends AbstractAuthenticationProcessingFilter {

    private final SuppressObjectMapper suppressObjectMapper;
    private final LdapClient ldapClient;

    public AdminLdapProviderFilter(String defaultFilterProcessesUrl,
                                   SuppressObjectMapper suppressObjectMapper,
                                   LdapClient ldapClient) {
        super(defaultFilterProcessesUrl);
        this.suppressObjectMapper = suppressObjectMapper;
        this.ldapClient = ldapClient;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        String collect = IOUtils.toString(request.getReader());
        SignInRequest signInRequest =
                suppressObjectMapper.readValue(collect, SignInRequest.class);
        if (ldapClient.authenticate(signInRequest.getUsername(), signInRequest.getPassword())) {
            return AuthenticationUtil.create(signInRequest.getUsername(), Role.ADMIN.name());
        }
        return null;
    }
}

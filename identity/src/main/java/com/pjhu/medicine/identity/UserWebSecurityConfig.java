package com.pjhu.medicine.identity;

import com.pjhu.medicine.identity.domain.model.AdminTokenRepository;
import com.pjhu.medicine.identity.domain.model.ExternalUserTokenRepository;
import com.pjhu.medicine.identity.domain.model.external.ExternalUserRepository;
import com.pjhu.medicine.common.utils.SuppressObjectMapper;
import com.pjhu.medicine.identity.filter.*;
import com.pjhu.medicine.identity.adapter.LdapClient;
import com.pjhu.medicine.identity.handler.AdminLoginSuccessHandler;
import com.pjhu.medicine.identity.handler.ExternalUserLoginSuccessHandler;
import com.pjhu.medicine.identity.handler.LogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.pjhu.medicine.common.utils.Constant.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class UserWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USER_API_PATTERN = USER + "/**";
    private static final String ADMIN_API_PATTERN = ADMIN + "/**";

    private final ExternalUserRepository externalUserRepository;
    private final SuppressObjectMapper suppressObjectMapper;
    private final ExternalUserTokenRepository externalUserTokenRepository;
    private final AdminTokenRepository adminTokenRepository;
    private final LdapClient ldapClient;

    @Autowired
    public UserWebSecurityConfig(ExternalUserTokenRepository externalUserTokenRepository,
                                 ExternalUserRepository externalUserRepository,
                                 SuppressObjectMapper suppressObjectMapper,
                                 AdminTokenRepository adminTokenRepository,
                                 LdapClient ldapClient) {
        this.externalUserRepository = externalUserRepository;
        this.suppressObjectMapper = suppressObjectMapper;
        this.externalUserTokenRepository = externalUserTokenRepository;
        this.adminTokenRepository = adminTokenRepository;
        this.ldapClient = ldapClient;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(USER_API_PATTERN).authenticated()
                .antMatchers(ADMIN_API_PATTERN).authenticated()
                .anyRequest().permitAll()
            .and()
                .addFilterBefore(externalUserTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(adminTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(externalUserProviderFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(externalUserLogoutFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(adminProviderFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(adminLogoutFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private ExternalUserTokenFilter externalUserTokenFilter() {
        return new ExternalUserTokenFilter(new AntPathRequestMatcher(USER_API_PATTERN), externalUserTokenRepository);
    }

    private AdminTokenFilter adminTokenFilter() {
        return new AdminTokenFilter(new AntPathRequestMatcher(ADMIN_API_PATTERN), adminTokenRepository);
    }

    private ExternalUserProviderFilter externalUserProviderFilter() {
        ExternalUserProviderFilter userProviderFilter =
                new ExternalUserProviderFilter(USER_SIGN_IN, externalUserRepository, suppressObjectMapper);
        userProviderFilter.setAuthenticationSuccessHandler(
                new ExternalUserLoginSuccessHandler(externalUserTokenRepository, suppressObjectMapper));
        return userProviderFilter;
    }

    private AdminProviderFilter adminProviderFilter() {
        AdminProviderFilter adminProvider = new AdminProviderFilter(ADMIN_SIGN_IN, suppressObjectMapper, ldapClient);
        adminProvider.setAuthenticationSuccessHandler(
                new AdminLoginSuccessHandler(adminTokenRepository, suppressObjectMapper));
        return adminProvider;
    }

    private ExternalUserLogoutFilter externalUserLogoutFilter() {
        ExternalUserLogoutFilter logoutFilter = new ExternalUserLogoutFilter(USER_SIGN_OUT, externalUserTokenRepository);
        logoutFilter.setAuthenticationSuccessHandler(new LogoutSuccessHandler());
        return logoutFilter;
    }

    private AdminLogoutFilter adminLogoutFilter() {
        AdminLogoutFilter logoutFilter = new AdminLogoutFilter(ADMIN_SIGN_OUT, adminTokenRepository);
        logoutFilter.setAuthenticationSuccessHandler(new LogoutSuccessHandler());
        return logoutFilter;
    }
}

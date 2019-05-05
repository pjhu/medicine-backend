package com.pjhu.medicine.infrastructure.security;

import com.pjhu.medicine.domain.auth.UserTokenRepository;
import com.pjhu.medicine.domain.user.external.ExternalUserRepository;
import com.pjhu.medicine.infrastructure.common.SuppressObjectMapper;
import com.pjhu.medicine.infrastructure.security.filter.*;
import com.pjhu.medicine.infrastructure.security.common.LdapClient;
import com.pjhu.medicine.infrastructure.security.handler.AdminLoginSuccessHandler;
import com.pjhu.medicine.infrastructure.security.handler.ExternalUserLoginSuccessHandler;
import com.pjhu.medicine.infrastructure.security.handler.LogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.pjhu.medicine.infrastructure.common.Constant.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class UserWebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String USER_API_PATTERN = USER + "/**";
    public static final String ADMIN_API_PATTERN = ADMIN + "/**";

    private final ExternalUserRepository externalUserRepository;
    private final SuppressObjectMapper suppressObjectMapper;
    private final UserTokenRepository userTokenRepository;
    private final LdapClient ldapClient;

    @Autowired
    public UserWebSecurityConfig(UserTokenRepository userTokenRepository,
                                 ExternalUserRepository externalUserRepository,
                                 SuppressObjectMapper suppressObjectMapper,
                                 LdapClient ldapClient) {
        this.externalUserRepository = externalUserRepository;
        this.suppressObjectMapper = suppressObjectMapper;
        this.userTokenRepository = userTokenRepository;
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
                .addFilterBefore(customJdbcProviderFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(externalUserLogoutFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(ldapProviderFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(adminLogoutFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private ExternalUserTokenFilter externalUserTokenFilter() {
        return new ExternalUserTokenFilter(new AntPathRequestMatcher(USER_API_PATTERN), userTokenRepository);
    }

    private AdminTokenFilter adminTokenFilter() {
        return new AdminTokenFilter(new AntPathRequestMatcher(ADMIN_API_PATTERN), userTokenRepository);
    }

    private ExternalUserJdbcProviderFilter customJdbcProviderFilter() {
        ExternalUserJdbcProviderFilter jdbcProvider =
                new ExternalUserJdbcProviderFilter(USER_SIGN_IN, externalUserRepository, suppressObjectMapper);
        jdbcProvider.setAuthenticationSuccessHandler(
                new ExternalUserLoginSuccessHandler(userTokenRepository, suppressObjectMapper));
        return jdbcProvider;
    }

    private AdminLdapProviderFilter ldapProviderFilter() {
        AdminLdapProviderFilter ldapProvider = new AdminLdapProviderFilter(ADMIN_SIGN_IN, suppressObjectMapper, ldapClient);
        ldapProvider.setAuthenticationSuccessHandler(
                new AdminLoginSuccessHandler(userTokenRepository, suppressObjectMapper));
        return ldapProvider;
    }

    private ExternalUserLogoutFilter externalUserLogoutFilter() {
        ExternalUserLogoutFilter logoutFilter = new ExternalUserLogoutFilter(USER_SIGN_OUT, userTokenRepository);
        logoutFilter.setAuthenticationSuccessHandler(new LogoutSuccessHandler());
        return logoutFilter;
    }

    private AdminLogoutFilter adminLogoutFilter() {
        AdminLogoutFilter logoutFilter = new AdminLogoutFilter(ADMIN_SIGN_OUT, userTokenRepository);
        logoutFilter.setAuthenticationSuccessHandler(new LogoutSuccessHandler());
        return logoutFilter;
    }
}

package com.pjhu.medicine.identity;

import com.pjhu.medicine.identity.domain.model.UserTokenRepository;
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

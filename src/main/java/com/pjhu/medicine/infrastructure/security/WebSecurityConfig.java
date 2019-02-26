package com.pjhu.medicine.infrastructure.security;

import com.pjhu.medicine.domain.auth.UserTokenRepository;
import com.pjhu.medicine.domain.operator.Role;
import com.pjhu.medicine.infrastructure.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MedicineTokenFilter medicineTokenFilter;

    @Autowired
    public WebSecurityConfig(UserTokenRepository userTokenRepository) {
        this.medicineTokenFilter = new MedicineTokenFilter(userTokenRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(Constant.ROOT + "/orders/**").hasRole(Role.ADMIN.name())
                .antMatchers(Constant.ROOT + "/catalogs/**").hasRole(Role.ADMIN.name())
                .antMatchers(Constant.ROOT + "/reports/**").hasRole(Role.ADMIN.name())
                .antMatchers(Constant.ROOT + "/auth/signIn").permitAll()
                .anyRequest().authenticated()
            .and()
                .addFilterBefore(medicineTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

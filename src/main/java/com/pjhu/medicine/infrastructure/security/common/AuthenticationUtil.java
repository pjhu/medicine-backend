package com.pjhu.medicine.infrastructure.security.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;

public class AuthenticationUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ROLE_PREFIX = "ROLE_";

    private AuthenticationUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Authentication create(String username, String role) {
        List<GrantedAuthority> authorities = createAuthorities(role);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, StringUtils.EMPTY, authorities);
        User user = new User(username, StringUtils.EMPTY, authorities);
        authenticationToken.setDetails(user);
        return authenticationToken;
    }

    private static List<GrantedAuthority> createAuthorities(String role) {
        return StringUtils.isBlank(role) ?
                Collections.emptyList() :
                Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + role));
    }

    public static String tokenExtract(String header, TokenType tokenType) {
        if (StringUtils.isBlank(header)) {
            return StringUtils.EMPTY;
        }

        return StringUtils.substringAfter(header, tokenType.getPrefix()).trim();
    }

}

package com.pjhu.medicine.identity.utils;

import com.pjhu.medicine.identity.domain.model.Role;
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

    private AuthenticationUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Authentication createAdmin(String username, String role) {
        List<GrantedAuthority> authorities = createAuthorities(role);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, StringUtils.EMPTY, authorities);
        User user = new User(username, StringUtils.EMPTY, authorities);
        authenticationToken.setDetails(user);
        return authenticationToken;
    }

    public static Authentication createExternalUser(String username) {
        List<GrantedAuthority> authorities = createAuthorities(Role.USER.name());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, StringUtils.EMPTY, authorities);
        User user = new User(username, StringUtils.EMPTY, authorities);
        authenticationToken.setDetails(user);
        return authenticationToken;
    }

    private static List<GrantedAuthority> createAuthorities(String role) {
        return StringUtils.isBlank(role) ?
                Collections.emptyList() :
                Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    public static String tokenExtract(String header, TokenType tokenType) {
        if (StringUtils.isBlank(header)) {
            return StringUtils.EMPTY;
        }

        return StringUtils.substringAfter(header, tokenType.getPrefix()).trim();
    }

}

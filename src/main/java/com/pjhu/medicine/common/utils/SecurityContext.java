package com.pjhu.medicine.common.utils;

import com.pjhu.medicine.user.domain.model.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


public class SecurityContext {

    public static final String ROLE_PREFIX = "ROLE_";
    private static final String USERNAME_SYS = "SYSTEM";

    public static String currentUsername() {
        //noinspection Convert2MethodRef
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(ctx -> ctx.getAuthentication())
                .map(auth -> auth.getName())
                .orElse(USERNAME_SYS);
    }

    public static String currentUserRole() {
        //noinspection Convert2MethodRef
        Collection<? extends GrantedAuthority> authorities = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(ctx -> ctx.getAuthentication())
                .map(auth -> auth.getAuthorities())
                .orElse(Collections.emptyList());
        if (authorities.isEmpty()) {
            return null;
        }

        //noinspection Convert2MethodRef
        return authorities.stream().filter(authority -> authority.getAuthority().startsWith(ROLE_PREFIX))
                .findFirst()
        .map(found -> found.getAuthority())
        .map(role -> role.substring(ROLE_PREFIX.length()))
        .orElse(null);
    }

    public static boolean currentHasRole(String role) {
        return StringUtils.equals(role, currentUserRole());
    }

    public static boolean isAdmin() {
        return Role.ADMIN.name().equals(currentUserRole());
    }

    public static boolean isUser() {
        return Role.USER.name().equals(currentUserRole());
    }
}

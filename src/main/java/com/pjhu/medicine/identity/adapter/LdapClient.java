package com.pjhu.medicine.identity.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

@Slf4j
@Service
@RequiredArgsConstructor
public class LdapClient {

    private final LdapContextSource ldapContextSource;

    public boolean authenticate(String username, String credentials) {
        DirContext ctx = null;
        try {
            String userDn = String.format("cn=%s, ou=medicine, dc=pjhu, dc=org", username);
            log.debug("the userDN: {}", userDn);
            ctx = ldapContextSource.getContext(userDn, credentials);
            ctx.lookup(userDn);
            log.info("Connected to LDAP server, and authenticated user [{}].", username);
            return true;
        } catch (NamingException | org.springframework.ldap.NamingException e) {
            log.error("Failed to connect to LDAP server", e);
            return false;
        } finally {
            LdapUtils.closeContext(ctx);
        }
    }
}

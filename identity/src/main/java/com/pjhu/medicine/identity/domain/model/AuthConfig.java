package com.pjhu.medicine.identity.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("internalAuthConfig")
@ConfigurationProperties("auth.admin")
@Setter
@Getter
public class AuthConfig {
    private TokenConfig token = new TokenConfig();

    public long getTokenExpireInterval() {
        return token.getExpire();
    }

    @Setter
    @Getter
    public static class TokenConfig {
        private long expire = 1800;
    }

}

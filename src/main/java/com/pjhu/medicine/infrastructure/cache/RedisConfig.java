package com.pjhu.medicine.infrastructure.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("redis")
@Component
@Setter
@Getter
public class RedisConfig {

    private String host;
    private int port;
}

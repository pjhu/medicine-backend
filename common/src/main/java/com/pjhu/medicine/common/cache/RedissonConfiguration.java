package com.pjhu.medicine.common.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.Math.toIntExact;

@Configuration
@RequiredArgsConstructor
public class RedissonConfiguration {

    private final ObjectMapper objectMapper;

    @Bean
    public RedissonClient redissonClient(@Autowired RedisProperties redisProperties) {
        Config config = buildConfig(redisProperties);
        return Redisson.create(config);
    }

    private Config buildConfig(RedisProperties redisProperties) {
        Config config = new Config();
        String address =
                String.format(
                        "%s://%s:%s", redisProperties.isSsl() ? "rediss" : "redis", redisProperties.getHost(),
                        redisProperties.getPort());
        config.useSingleServer()
                .setDatabase(redisProperties.getDatabase())
                .setSslEnableEndpointIdentification(false)
                .setTimeout(toIntExact(redisProperties.getTimeout().toMillis()))
                .setAddress(address);
        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            config.useSingleServer().setPassword(redisProperties.getPassword());
        }
        config.setCodec(new JsonJacksonCodec(objectMapper));
        return config;
    }
}

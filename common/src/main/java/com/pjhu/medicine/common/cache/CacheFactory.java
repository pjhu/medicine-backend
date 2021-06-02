package com.pjhu.medicine.common.cache;

import com.pjhu.medicine.common.utils.SuppressObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.cache.CacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@Configuration
@RequiredArgsConstructor
public class CacheFactory {

    @Value("${cache.token.ttl:2h}")
    private String tokenTTL;

    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory, SuppressObjectMapper objectMapper) {
        return getStringRedisCacheManager(connectionFactory, objectMapper, tokenTTL);
    }

    private CacheManager getStringRedisCacheManager(RedisConnectionFactory connectionFactory,
                                                    SuppressObjectMapper objectMapper, String ttl) {
        StringRedisSerializer serializer = new StringRedisSerializer();

        if (Objects.isNull(connectionFactory)) {
            throw new NullPointerException("connection factory is null");
        }
        RedisCacheWriter redisCacheWriter =
                RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration redisCacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(DurationStyle.SIMPLE.parse(ttl))
                        .serializeValuesWith(fromSerializer(serializer));
        RedisCacheManager redisCacheManager =
                new RedisCacheManager(redisCacheWriter, redisCacheConfiguration) {
                    @Override
                    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
                        return new StringRedisCache(super.createRedisCache(name, cacheConfig), objectMapper,
                                connectionFactory);
                    }
                };
        redisCacheManager.setTransactionAware(true);
        return new TransactionAwareCacheManagerProxy(redisCacheManager);
    }
}

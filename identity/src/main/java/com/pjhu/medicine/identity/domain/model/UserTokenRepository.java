package com.pjhu.medicine.identity.domain.model;

import com.pjhu.medicine.common.cache.UserMeta;
import com.pjhu.medicine.identity.utils.UuidHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class UserTokenRepository {

    private final RedisTemplate<String, UserMeta> redisTemplate;
    private final AuthConfig authConfig;

    @Autowired
    public UserTokenRepository(RedisTemplate redisTemplate, AuthConfig authConfig) {
        //noinspection unchecked
        this.redisTemplate = redisTemplate;
        this.authConfig = authConfig;
    }

    public String create(String namespace, UserMeta userMeta) {
        String tokenId = UuidHelper.uuid32();
        redisTemplate.opsForValue()
                .set(toKey(namespace, tokenId),
                        userMeta, authConfig.getTokenExpireInterval(), TimeUnit.SECONDS);
        return tokenId;
    }

    public UserMeta getBy(String namespace, String tokenId) {
        return redisTemplate.opsForValue().get(toKey(namespace, tokenId));
    }

    public void delete(String namespace, String tokenId) {
        redisTemplate.delete(toKey(namespace, tokenId));
    }

    public void refresh(String namespace, String tokenId) {
        redisTemplate.expire(toKey(namespace, tokenId), authConfig.getTokenExpireInterval(), TimeUnit.SECONDS);
    }

    private String toKey(String namespace, String tokenId) {
        return String.format("%s:%s", namespace, tokenId);
    }
}

package com.pjhu.medicine.domain.auth;

import com.pjhu.medicine.domain.operator.Operator;
import com.pjhu.medicine.infrastructure.cache.OperatorMeta;
import com.pjhu.medicine.infrastructure.common.UuidHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class UserTokenRepository {

    private static final String NAME_SPACE = "medicine:auth:token:admin";

    private final RedisTemplate<String, OperatorMeta> redisTemplate;
    private final AuthConfig authConfig;

    @Autowired
    public UserTokenRepository(RedisTemplate redisTemplate, AuthConfig authConfig) {
        //noinspection unchecked
        this.redisTemplate = redisTemplate;
        this.authConfig = authConfig;
    }

    public String create(Operator operator) {
        OperatorMeta operatorMeta = OperatorMeta.of(operator);
        String tokenId = UuidHelper.uuid32();
        redisTemplate.opsForValue()
                .set(toKey(tokenId), operatorMeta, authConfig.getTokenExpireInterval(), TimeUnit.SECONDS);
        return tokenId;
    }

    public OperatorMeta getBy(String tokenId) {
        return redisTemplate.opsForValue().get(toKey(tokenId));
    }

    public void delete(String tokenId) {
        redisTemplate.delete(toKey(tokenId));
    }

    public void refresh(String tokenId) {
        redisTemplate.expire(toKey(tokenId), authConfig.getTokenExpireInterval(), TimeUnit.SECONDS);
    }

    private String toKey(String tokenId) {
        return String.format("%s:%s", NAME_SPACE, tokenId);
    }
}

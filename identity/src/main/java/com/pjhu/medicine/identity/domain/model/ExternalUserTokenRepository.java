package com.pjhu.medicine.identity.domain.model;

import com.pjhu.medicine.common.cache.CacheName;
import com.pjhu.medicine.common.cache.ExternalUserMeta;
import com.pjhu.medicine.identity.utils.TokenType;
import com.pjhu.medicine.identity.utils.UuidHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ExternalUserTokenRepository {

    private Cache cache;

    @Value("${cache.token.ttl:2h}")
    private String tokenTTL;

    @Autowired
    public ExternalUserTokenRepository(CacheManager cacheManager) {
        this.cache = cacheManager.getCache(CacheName.TOKEN.name());
    }

    public String create(String namespace, ExternalUserMeta externalUserMeta) {
        String tokenId = UuidHelper.uuid32();
        String tokenKey = toKey(namespace, tokenId);
        cache.put(tokenKey, externalUserMeta);
        return String.format("%s %s", TokenType.USER.getPrefix(), tokenId);
    }

    public ExternalUserMeta getBy(String namespace, String tokenId) {
        String tokenKey = toKey(namespace, tokenId);
        return cache.get(tokenKey, ExternalUserMeta.class);
    }

    public void delete(String namespace, String tokenId) {
        String tokenKey = toKey(namespace, tokenId);
        cache.evict(tokenKey);
    }

    public void refresh(String namespace, String tokenId) {
        String tokenKey = toKey(namespace, tokenId);
        cache.put(tokenKey, cache.get(tokenKey, ExternalUserMeta.class));
    }

    private String toKey(String namespace, String tokenId) {
        return String.format("%s:%s", namespace, tokenId);
    }
}
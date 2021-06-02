package com.pjhu.medicine.common.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.pjhu.medicine.common.cache.RedisNamespace.Lock_NAME_SPACE;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistributedLockService {

    private static final Duration MAX_WAIT_LOCK_TIME = Duration.ofSeconds(10);
    private static final Duration MAX_LEASE_TIME = Duration.ofSeconds(5);

    private final RedissonClient redissonClient;

    public void lockKeyAndRun(String key, Runnable runnable) {
        lockKeyAndRun(key, runnable, MAX_LEASE_TIME);
    }

    public void lockKeyAndRun(String key, Runnable runnable, Duration maxRunTime) {
        this.lockByKey(key, maxRunTime);
        try {
            runnable.run();
        } finally {
            this.unlockByKey(key);
        }
    }

    public <T> T lockKeyAndRun(String key, Supplier<T> runnable) {
        return lockKeyAndRun(key, runnable, MAX_LEASE_TIME);
    }

    public <T> T lockKeyAndRun(String key, Supplier<T> runnable, Duration maxRunTime) {
        this.lockByKey(key, maxRunTime);
        try {
            return runnable.get();
        } finally {
            this.unlockByKey(key);
        }
    }

    private RLock lockByKey(String key, Duration maxLease) {
        return Optional.ofNullable(key)
                .map(item -> tryLock(item, MAX_WAIT_LOCK_TIME.toMillis(), maxLease.toMillis()))
                .orElseThrow(() -> new RuntimeException("key is empty"));
    }

    private void unlockByKey(String key) {
        Optional.ofNullable(key)
                .ifPresent(item -> redissonClient.getLock(buildKeyWithNamespace(item)).unlock());
    }

    private RLock tryLock(String lockKey, long waitTime, long leaseTime) {
        String lockKeyWithNamespace = buildKeyWithNamespace(lockKey);
        RLock lock = redissonClient.getLock(lockKeyWithNamespace);
        try {
            if (lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS)) {
                return lock;
            } else {
                throw new RuntimeException("get lock failed for key: " + lockKey);
            }
        } catch (InterruptedException exception) {
            throw new RuntimeException("get lock failed for key: " + lockKey, exception);
        }
    }

    private String buildKeyWithNamespace(String lockKey) {
        return Lock_NAME_SPACE + ":" + lockKey;
    }
}

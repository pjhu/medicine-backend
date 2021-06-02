package com.pjhu.medicine.blacklist.application.service;

import com.pjhu.medicine.blacklist.application.service.command.AddToBlackListCommand;
import com.pjhu.medicine.blacklist.application.service.command.CheckInBlacklistCommand;
import com.pjhu.medicine.blacklist.application.service.response.CheckInBlacklistResponse;
import com.pjhu.medicine.blacklist.domain.Blacklist;
import com.pjhu.medicine.blacklist.domain.BlacklistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlacklistApplicationService {

    private final BlacklistRepository blacklistRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public void addToBlackList(AddToBlackListCommand command) {
        boolean existed = blacklistRepository.existsByPhone(command.getPhone());
        if (!existed) {
            long head = command.getPhone() / 100000000L;
            long end = command.getPhone() % 100000000L;
            redissonClient.getBitSet("B" + head).set(end,true);
            blacklistRepository.save(new Blacklist(command.getPhone()));
        }
    }

    @Transactional
    public CheckInBlacklistResponse checkInBlackList(CheckInBlacklistCommand command) {
        long head = command.getPhone() / 100000000L;
        long end = command.getPhone() % 100000000L;
        boolean p159 = redissonClient.getBitSet("B" + head).get(end);
        log.info(String.valueOf(p159));
        boolean existed = blacklistRepository.existsByPhone(command.getPhone());
        return CheckInBlacklistResponse.builder().existed(existed).build();
    }
}

package com.pjhu.medicine.domain.operator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperatorManager {

    private final OperatorRepository operatorRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public Operator findOperatorBy(String username) {
        return operatorRepository.findByUsernameAndActiveNot(username, UserStatus.DISABLE);
    }
}

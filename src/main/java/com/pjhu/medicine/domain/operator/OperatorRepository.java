package com.pjhu.medicine.domain.operator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OperatorRepository extends JpaRepository<Operator, String>, JpaSpecificationExecutor<Operator> {
    Operator findByUsernameAndActiveNot(String username, UserStatus status);
}

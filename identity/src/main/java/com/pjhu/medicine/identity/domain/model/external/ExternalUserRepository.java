package com.pjhu.medicine.identity.domain.model.external;

import com.pjhu.medicine.identity.domain.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExternalUserRepository extends JpaRepository<ExternalUser, String>, JpaSpecificationExecutor<ExternalUser> {
    ExternalUser findByUsernameAndActiveNot(String username, UserStatus status);
}

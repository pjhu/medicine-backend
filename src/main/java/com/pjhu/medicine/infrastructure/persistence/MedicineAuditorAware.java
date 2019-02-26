package com.pjhu.medicine.infrastructure.persistence;

import com.pjhu.medicine.infrastructure.common.SecurityContext;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * https://docs.spring.io/spring-data/jpa/docs/2.0.3.RELEASE/reference/html/
 */
public class MedicineAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityContext.currentUsername());
    }
}

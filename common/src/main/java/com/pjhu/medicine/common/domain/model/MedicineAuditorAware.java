package com.pjhu.medicine.common.domain.model;

import com.pjhu.medicine.common.utils.SecurityContext;
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

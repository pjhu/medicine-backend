package com.pjhu.medicine.common.domain.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * https://docs.spring.io/spring-data/jpa/docs/2.0.3.RELEASE/reference/html/
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditor() {
        return new MedicineAuditorAware();
    }
}

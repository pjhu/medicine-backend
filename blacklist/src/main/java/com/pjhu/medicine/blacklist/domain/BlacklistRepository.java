package com.pjhu.medicine.blacklist.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

    boolean existsByPhone(Long phone);
}

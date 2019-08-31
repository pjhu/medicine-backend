package com.pjhu.medicine.cms.domain.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContentQueryRepository {

    Optional<Content> getOrderBy(long id);

    Page<Content> findAll(Pageable pageable);
}

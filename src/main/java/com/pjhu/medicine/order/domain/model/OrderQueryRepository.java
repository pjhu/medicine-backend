package com.pjhu.medicine.order.domain.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderQueryRepository {

    Optional<Order> getOrderBy(long id);

    Page<Order> findAll(Pageable pageable);
}
package com.pjhu.medicine.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderManager {

    @Transactional(readOnly = true)
    public Order getOrder(String id) {
        return Order.builder()
                .id(id)
                .name("name")
                .shortDescription("short description")
                .longDescription("long descriptin")
                .build();
    }
}

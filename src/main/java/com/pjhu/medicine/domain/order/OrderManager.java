package com.pjhu.medicine.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderManager {

    private OrderRepository orderRepository;

    @Autowired
    public OrderManager(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public Order getOrder(String id) {
        return Order.builder()
                .name("name")
                .quantity("short description")
                .totalPrice("long description")
                .build();
    }

    @Transactional
    public String create(OrderCreateCommand command) {
        Order order = command.newOrder(command);
        orderRepository.save(order);
        return order.getId();
    }
}

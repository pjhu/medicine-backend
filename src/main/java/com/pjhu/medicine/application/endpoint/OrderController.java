package com.pjhu.medicine.application.endpoint;

import com.pjhu.medicine.domain.order.Order;
import com.pjhu.medicine.domain.order.OrderManager;
import com.pjhu.medicine.domain.order.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/orders", produces = APPLICATION_JSON_UTF8_VALUE)
public class OrderController {

    private final OrderManager orderManager;

    @Autowired
    public OrderController(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @RequestMapping("/{id}")
    public OrderResponse getOrder(@PathVariable("id") String id) {
        Order order = orderManager.getOrder(id);
        return OrderResponse.builder()
                .name(order.getName())
                .shortDescription(order.getShortDescription())
                .build();
    }
}

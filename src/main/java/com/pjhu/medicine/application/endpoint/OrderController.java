package com.pjhu.medicine.application.endpoint;

import com.pjhu.medicine.domain.order.Order;
import com.pjhu.medicine.domain.order.OrderCreateCommand;
import com.pjhu.medicine.domain.order.OrderManager;
import com.pjhu.medicine.domain.order.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/orders", produces = APPLICATION_JSON_UTF8_VALUE)
public class OrderController {

    private final OrderManager orderManager;

    @Autowired
    public OrderController(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable("id") String id) {
        Order order = orderManager.getOrder(id);
        return OrderResponse.builder()
                .name(order.getName())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody OrderCreateCommand command) {
        String orderId = orderManager.create(command);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderId).toUri();
        return ResponseEntity.created(uri).build();
    }


}

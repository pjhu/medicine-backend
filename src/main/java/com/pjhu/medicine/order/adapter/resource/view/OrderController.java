package com.pjhu.medicine.order.adapter.resource.view;

import com.pjhu.medicine.order.domain.model.Order;
import com.pjhu.medicine.order.application.service.OrderCreateCommand;
import com.pjhu.medicine.order.application.service.OrderManager;
import com.pjhu.medicine.order.application.service.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.pjhu.medicine.identity.utils.Constant.ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = ADMIN + "/orders", produces = APPLICATION_JSON_UTF8_VALUE)
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
                .totalPrice(order.getTotalPrice())
                .build();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody OrderCreateCommand command) {
        Long orderId = orderManager.create(command);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderId).toUri();
        return ResponseEntity.created(uri).build();
    }

}

package com.pjhu.medicine.order.adapter.resource.view;

import com.pjhu.medicine.order.application.service.OrderQueryService;
import com.pjhu.medicine.order.application.service.command.OrderCreateCommand;
import com.pjhu.medicine.order.application.service.OrderApplicationService;
import com.pjhu.medicine.order.application.service.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.pjhu.medicine.common.utils.Constant.ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = ADMIN + "/orders", produces = APPLICATION_JSON_UTF8_VALUE)
public class OrderController {

    private final OrderApplicationService orderApplicationService;
    private final OrderQueryService orderQueryService;

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable("id") Long id) {
        return orderQueryService.getOrder(id);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getOrderByPage(Pageable pageable) {
        Page<OrderResponse> page = orderQueryService.getOrderByPage(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<Object> placeOrder(@RequestBody OrderCreateCommand command) {
        Long orderId = orderApplicationService.placeOrder(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

}

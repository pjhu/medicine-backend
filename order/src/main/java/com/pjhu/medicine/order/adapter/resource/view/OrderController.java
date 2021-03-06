package com.pjhu.medicine.order.adapter.resource.view;

import com.pjhu.medicine.order.application.service.OrderQueryService;
import com.pjhu.medicine.order.application.service.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pjhu.medicine.common.utils.Constant.ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = ADMIN + "/orders", produces = APPLICATION_JSON_UTF8_VALUE)
public class OrderController {

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
}

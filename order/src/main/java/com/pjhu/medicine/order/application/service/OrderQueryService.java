package com.pjhu.medicine.order.application.service;

import com.pjhu.medicine.order.application.service.response.OrderResponse;
import com.pjhu.medicine.order.domain.model.Order;
import com.pjhu.medicine.order.domain.model.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderQueryService {

    private final OrderQueryRepository orderQueryRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public OrderResponse getOrder(long id) {
        Optional<Order> order = orderQueryRepository.getOrderBy(id);
        return order
                .map(e -> OrderResponse.builder()
                        .orderId(e.getId())
                        .totalPrice(e.getOrderAmountTotal())
                        .build())
                .orElse(OrderResponse.builder().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public Page<OrderResponse> getOrderByPage(Pageable pageable) {
        Page<Order> orderPage = orderQueryRepository.findAll(pageable);
        return orderPage.map(e -> OrderResponse.builder()
                .orderId(e.getId())
                .totalPrice(e.getOrderAmountTotal())
                .build());
    }
}

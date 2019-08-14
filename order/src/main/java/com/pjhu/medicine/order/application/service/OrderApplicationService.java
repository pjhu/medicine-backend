package com.pjhu.medicine.order.application.service;

import com.pjhu.medicine.order.adapter.service.ProductApplicationServiceAdapter;
import com.pjhu.medicine.order.adapter.service.ProductDto;
import com.pjhu.medicine.order.application.service.command.OrderCreateCommand;
import com.pjhu.medicine.order.domain.model.Order;
import com.pjhu.medicine.order.domain.model.OrderProductDetail;
import com.pjhu.medicine.order.domain.model.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderRepository orderRepository;
    private final ProductApplicationServiceAdapter productServiceAdapter;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Long create(OrderCreateCommand command) {
        Order order = new Order();
        ProductDto productDto = productServiceAdapter.getProductBy(command.getProductId(), command.getSku());
        OrderProductDetail orderProductDetail = new OrderProductDetail(command.getSku(),
                productDto.getProductName(), productDto.getPrice(), command.getQuantity());
        Order placeOrder = order.placeOrder(command.getQuantity(), productDto.getPrice(),
                productDto.getPrice(), Collections.singletonList(orderProductDetail));
        orderRepository.save(placeOrder);
        return order.getId();
    }
}

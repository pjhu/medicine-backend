package com.pjhu.medicine.order.application.service;

import com.pjhu.medicine.common.cache.DistributedLockService;
import com.pjhu.medicine.order.adapter.service.ProductApplicationServiceAdapter;
import com.pjhu.medicine.order.adapter.service.ProductDto;
import com.pjhu.medicine.order.application.service.command.OrderCreateCommand;
import com.pjhu.medicine.order.domain.model.Order;
import com.pjhu.medicine.order.domain.model.OrderProductDetail;
import com.pjhu.medicine.order.domain.model.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderRepository orderRepository;
    private final ProductApplicationServiceAdapter productServiceAdapter;
    private final DistributedLockService distributedLockService;

    @Transactional
    public Long placeOrder(OrderCreateCommand command) {
        Order order = new Order();
        ProductDto productDto = productServiceAdapter.getProductBy(command.getProductId(), command.getSku());
        OrderProductDetail orderProductDetail = new OrderProductDetail(command.getSku(),
                productDto.getProductName(), productDto.getPrice(), command.getQuantity());
        Order placeOrder = order.placeOrder(command.getQuantity(), productDto.getPrice(),
                productDto.getPrice(), Collections.singletonList(orderProductDetail));

        distributedLockService.lockKeyAndRun("storage",
                () -> System.out.println("test distribute lock"), Duration.parse("PT5s"));

        orderRepository.save(placeOrder);
        return placeOrder.getId();
    }
}

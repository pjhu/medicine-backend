package com.pjhu.medicine.order.application.service;

import com.pjhu.medicine.common.cache.DistributedLockService;
import com.pjhu.medicine.order.adapter.service.ProductApplicationServiceAdapter;
import com.pjhu.medicine.order.adapter.service.ProductDto;
import com.pjhu.medicine.order.application.service.command.OrderCreateCommand;
import com.pjhu.medicine.order.application.service.response.OrderResponse;
import com.pjhu.medicine.order.domain.model.Order;
import com.pjhu.medicine.order.domain.model.OrderProductDetail;
import com.pjhu.medicine.order.domain.model.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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

        // 存储为hash, key为: LOCK::medicine:storage
        // field："8d836c0a-e6b7-4b84-8231-fb82a8bb43e8:78" 是uuid + thread-id
        // value: "1"
        distributedLockService.lockKeyAndRun("storage",
                this::distributedLockTest, Duration.parse("PT50s"));

        orderRepository.save(placeOrder);
        return placeOrder.getId();
    }

    // 默认使用SimpleCacheConfiguration, 使用ConcurrentMapCacheManager 存储信息，没有过期时间
    @Cacheable(cacheNames = "orders", key = "#orderId", cacheManager = "annotationCacheManager")
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(e -> OrderResponse.builder()
                        .orderId(e.getId())
                        .totalPrice(e.getOrderAmountTotal())
                        .build())
                .orElse(OrderResponse.builder().build());
    }

    private void distributedLockTest() {
        System.out.println("test distribute lock");
    }

}

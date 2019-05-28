package com.pjhu.medicine.order.application.service;

import com.pjhu.medicine.common.notification.email.AliyunEmailClient;
import com.pjhu.medicine.common.notification.email.SendEmailCommand;
import com.pjhu.medicine.order.adapter.OrderCatalogAdapter;
import com.pjhu.medicine.order.domain.model.Order;
import com.pjhu.medicine.order.domain.model.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderManager {

    private final OrderRepository orderRepository;
    private final AliyunEmailClient emailClient;
    private final OrderCatalogAdapter catalogAdapter;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public Order getOrder(String id) {
        return Order.builder()
                .totalPrice("123")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Long create(OrderCreateCommand command) {
        SecurityContext context = SecurityContextHolder.getContext();
        Order order = command.newOrder();
        catalogAdapter.getCatalogBy(command.getCatalogId());
        orderRepository.save(order);
        SendEmailCommand emailCommand = SendEmailCommand.builder()
                .accountName("account name")
                .build();
        // emailClient.send(emailCommand);
        log.info("send email: " + emailCommand.toString());
        return order.getId();
    }
}

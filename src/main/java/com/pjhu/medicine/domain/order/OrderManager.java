package com.pjhu.medicine.domain.order;

import com.pjhu.medicine.infrastructure.notification.email.AliyunEmailClient;
import com.pjhu.medicine.infrastructure.notification.email.SendEmailCommand;
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

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public Order getOrder(String id) {
        return Order.builder()
                .name("name")
                .quantity("short description")
                .totalPrice("long description")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public String create(OrderCreateCommand command) {
        SecurityContext context = SecurityContextHolder.getContext();
        Order order = command.newOrder();
        orderRepository.save(order);
        SendEmailCommand emailCommand = SendEmailCommand.builder()
                .accountName("account name")
                .build();
        emailClient.send(emailCommand);
        log.info("send email: " + emailCommand.toString());
        return order.getId();
    }
}

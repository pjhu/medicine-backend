package com.pjhu.medicine.domain.order;

import com.pjhu.medicine.infrastructure.notification.email.AliyunEmailClient;
import com.pjhu.medicine.infrastructure.notification.email.SendEmailCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderManager {

    private final OrderRepository orderRepository;
    private final AliyunEmailClient emailClient;

    @Transactional(readOnly = true)
    public Order getOrder(String id) {
        return Order.builder()
                .name("name")
                .quantity("short description")
                .totalPrice("long description")
                .build();
    }

    @Transactional
    public String create(OrderCreateCommand command) {
        Order order = command.newOrder(command);
        orderRepository.save(order);
        SendEmailCommand emailCommand = SendEmailCommand.builder()
                .accountName("account name")
                .build();
        emailClient.send(emailCommand);
        return order.getId();
    }
}

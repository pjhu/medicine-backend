package com.pjhu.medicine.order.application.service;

import com.pjhu.medicine.order.adapter.service.CatalogApplicationServiceAdapter;
import com.pjhu.medicine.order.adapter.service.CatalogDto;
import com.pjhu.medicine.order.application.service.command.OrderCreateCommand;
import com.pjhu.medicine.order.domain.model.Order;
import com.pjhu.medicine.order.domain.model.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderRepository orderRepository;
    private final CatalogApplicationServiceAdapter catalogAdapter;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Long create(OrderCreateCommand command) {
        Order order = new Order();
        CatalogDto catalogDto = catalogAdapter.getCatalogBy(command.getCatalogId());
        Order placeOrder = order.placeOrder(command.getCatalogId(), command.getQuantity(), command.getTotalPrice());
        Order newOrder = orderRepository.save(placeOrder);
        return newOrder.getId();
    }
}

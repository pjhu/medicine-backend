package com.pjhu.medicine.order.application.service;

import com.pjhu.medicine.order.domain.model.Order;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateCommand {

    private long catalogId;
    private String totalPrice;
    private Integer quantity;
    private String address;

    public Order newOrder() {
        return Order.builder()
                .catalogId(getCatalogId())
                .quantity(getQuantity().toString())
                .totalPrice(getTotalPrice())
                .build();
    }
}

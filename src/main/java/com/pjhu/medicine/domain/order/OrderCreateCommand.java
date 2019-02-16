package com.pjhu.medicine.domain.order;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateCommand {

    private String name;
    private String totalPrice;
    private Integer quantity;
    private String address;

    public Order newOrder() {
        return Order.builder()
                .name(getName())
                .quantity(getQuantity().toString())
                .totalPrice(getTotalPrice())
                .build();
    }
}

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

    public Order newOrder(OrderCreateCommand command) {
        return Order.builder()
                .name(command.name)
                .quantity(command.getQuantity().toString())
                .totalPrice(command.getTotalPrice())
                .build();
    }
}

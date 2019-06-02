package com.pjhu.medicine.order.application.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}

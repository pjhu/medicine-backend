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

    private Long productId;
    private String sku;
    private Integer quantity;
    private String address;
}

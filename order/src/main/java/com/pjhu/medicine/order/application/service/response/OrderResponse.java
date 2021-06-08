package com.pjhu.medicine.order.application.service.response;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse implements Serializable {

    private long orderId;
    private Integer totalPrice;
}

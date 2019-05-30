package com.pjhu.medicine.order.application.service.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderResponse {

    private long orderId;
    private String totalPrice;
}

package com.pjhu.medicine.order.application.service;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderResponse {

    private String totalPrice;
}

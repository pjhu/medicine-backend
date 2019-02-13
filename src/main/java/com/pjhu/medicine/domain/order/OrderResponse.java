package com.pjhu.medicine.domain.order;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderResponse {

    private String name;
    private String shortDescription;
}

package com.pjhu.medicine.order.domain.model;

import lombok.*;

import javax.persistence.Embeddable;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderProductDetail {

    private String productSku;
    private String productName;
    private Integer productPrice;
    private Integer number;
}

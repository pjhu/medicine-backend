package com.pjhu.medicine.order.domain.model;

import com.pjhu.medicine.common.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ORDER")
@Builder
@Getter
@Setter
@NoArgsConstructor
public class Order extends AbstractEntity {

    private long catalogId;
    private Integer quantity;
    private String totalPrice;

    @Builder
    public Order(long catalogId, Integer quantity, String totalPrice) {
        super();
        this.catalogId = catalogId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Order placeOrder(long catalogId, Integer quantity, String totalPrice) {
        return new Order(catalogId, quantity, totalPrice);
    }
}

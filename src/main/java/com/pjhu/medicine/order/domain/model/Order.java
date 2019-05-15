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
@AllArgsConstructor
@NoArgsConstructor
public class Order extends AbstractEntity {

    private String name;
    private String quantity;
    private String totalPrice;
}

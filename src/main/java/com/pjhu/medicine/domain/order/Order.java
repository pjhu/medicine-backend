package com.pjhu.medicine.domain.order;

import com.pjhu.medicine.infrastructure.persistence.AbstractEntity;
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

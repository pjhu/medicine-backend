package com.pjhu.medicine.domain.order;

import com.pjhu.medicine.infrastructure.persistence.Entity;
import lombok.*;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "order")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends Entity {

    private String name;
    private String shortDescription;
    private String longDescription;
}

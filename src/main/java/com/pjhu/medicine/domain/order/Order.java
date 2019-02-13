package com.pjhu.medicine.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String id;
    private String name;
    private String shortDescription;
    private String longDescription;
}

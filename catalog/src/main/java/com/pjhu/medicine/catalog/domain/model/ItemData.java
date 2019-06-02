package com.pjhu.medicine.catalog.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemData {

    private String sku;
    private String name;
    private String description;
    private String note;
    private Long stock;

    public Catalog newCatalog() {
        return Catalog.builder()
                .sku(getSku())
                .name(getName())
                .description(getDescription())
                .note(getNote())
                .stock(getStock())
                .build();
    }
}

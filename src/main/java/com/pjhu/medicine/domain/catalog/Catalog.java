package com.pjhu.medicine.domain.catalog;

import com.pjhu.medicine.infrastructure.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "catalog")
@Getter
@Setter
@Builder
public class Catalog extends Entity {

    private String sku;
    private String name;
    private String description;
    private String note;
    private Long stock;

    public void update(ItemData itemData) {
        this.name = itemData.getName();
        this.description = itemData.getDescription();
        this.note = itemData.getNote();
        this.stock = itemData.getStock();
    }
}

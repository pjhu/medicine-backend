package com.pjhu.medicine.catalog.domain.model;

import com.pjhu.medicine.common.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "catalog")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Catalog extends AbstractEntity {

    private String sku;
    private String name;
    private String description;
    private String note;
    private Long stock;

    public Catalog upsert(List<Catalog> existingList, ItemData itemData) {
        Catalog entity;
        switch (existingList.size()) {
            case 0:
                entity = itemData.newCatalog();
                break;
            case 1:
                Catalog catalog = existingList.get(0);
                this.name = itemData.getName();
                this.description = itemData.getDescription();
                this.note = itemData.getNote();
                this.stock = itemData.getStock();
                entity = catalog;
                break;
            default:
                throw new RuntimeException();
        }
        return entity;
    }
}

package com.pjhu.medicine.product.domain.model;

import com.pjhu.medicine.common.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractEntity {

    private String name;
    private String brand;
    private String description;
    private ProductStatus productStatus;
    @ElementCollection
    @CollectionTable(
            name = "product_sku",
            joinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductSku> skus = new ArrayList<>();

    public void upsert(List<Product> existingList) {
        switch (existingList.size()) {
            case 0:
                productStatus = ProductStatus.UN_PUBLISH;
                break;
            case 1:
                skus = existingList.get(0).getSkus();
                productStatus = ProductStatus.UN_PUBLISH;
                break;
            default:
                throw new RuntimeException();
        }
    }
}

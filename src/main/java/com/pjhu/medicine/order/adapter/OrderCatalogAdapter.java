package com.pjhu.medicine.order.adapter;

import com.pjhu.medicine.catalog.application.service.CatalogManager;
import com.pjhu.medicine.catalog.domain.model.Catalog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderCatalogAdapter {

    private final CatalogManager catalogManager;

    public Catalog getCatalogBy(long id) {
        Optional<Catalog> catalog = catalogManager.getCatalogBy(id);
        return catalog
                .map(e -> Catalog.builder()
                        .sku(e.getSku())
                        .name(e.getName())
                        .build())
                .orElse(Catalog.builder().build());
    }
}

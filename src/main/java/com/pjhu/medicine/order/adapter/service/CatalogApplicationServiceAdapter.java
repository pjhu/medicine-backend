package com.pjhu.medicine.order.adapter.service;

import com.pjhu.medicine.catalog.application.service.CatalogQueryApplicationService;
import com.pjhu.medicine.catalog.domain.model.Catalog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogApplicationServiceAdapter {

    private final CatalogQueryApplicationService catalogQueryApplicationService;

    public CatalogDto getCatalogBy(long id) {
        Optional<Catalog> catalog = catalogQueryApplicationService.getCatalogBy(id);
        return catalog
                .map(e -> CatalogDto.builder()
                        .sku(e.getSku())
                        .build())
                .orElse(CatalogDto.builder().build());
    }
}

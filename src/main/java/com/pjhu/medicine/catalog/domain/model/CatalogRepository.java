package com.pjhu.medicine.catalog.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    List<Catalog> findAllBySku(String sku);
}

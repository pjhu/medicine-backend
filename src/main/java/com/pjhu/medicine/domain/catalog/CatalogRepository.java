package com.pjhu.medicine.domain.catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogRepository extends JpaRepository<Catalog, String> {
    List<Catalog> findAllBySku(String sku);
}

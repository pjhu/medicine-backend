package com.pjhu.medicine.catalog.application.service;

import com.pjhu.medicine.catalog.domain.model.Catalog;
import com.pjhu.medicine.catalog.domain.model.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class CatalogQueryApplicationService {

    private final CatalogRepository catalogRepository;

    public Optional<Catalog> getCatalogBy(long id) {
        return catalogRepository.findById(id);
    }
}

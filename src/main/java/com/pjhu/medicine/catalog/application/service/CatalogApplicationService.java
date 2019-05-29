package com.pjhu.medicine.catalog.application.service;

import com.pjhu.medicine.catalog.application.service.command.BatchUpdateCommand;
import com.pjhu.medicine.catalog.domain.model.Catalog;
import com.pjhu.medicine.catalog.domain.model.CatalogRepository;
import com.pjhu.medicine.catalog.domain.model.ItemData;
import com.pjhu.medicine.catalog.domain.model.ItemDataParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogApplicationService {

    private final ItemDataParser<MultipartFile> itemDataParser;
    private final CatalogRepository catalogRepository;

    @Transactional
    public void batchUpdate(BatchUpdateCommand command) {
        List<ItemData> parse = itemDataParser.parse(command.getFile());

        List<Catalog> result = parse.stream()
                .map(this::upsertCatalogByData)
                .map(catalogRepository::save)
                .collect(Collectors.toList());
        log.info("Imported [{}] catalog from the excel.", result.size());
    }

    private Catalog upsertCatalogByData(ItemData itemData) {
        log.info("Updating a catalog with code [{}].", itemData.getSku());
        List<Catalog> existingList = catalogRepository.findAllBySku(itemData.getSku());
        Catalog entity;
        switch (existingList.size()) {
            case 0:
                entity = itemData.newCatalog();
                break;
            case 1:
                Catalog catalog = existingList.get(0);
                catalog.update(itemData);
                entity = catalog;
                break;
            default:
                throw new RuntimeException();
        }
        return entity;
    }
}

package com.pjhu.medicine.application.endpoint;

import com.pjhu.medicine.domain.catalog.BatchUpdateCommand;
import com.pjhu.medicine.domain.catalog.CatalogManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/catalogs", produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogManager catalogManager;

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public void batchUpdateCatalog(@ModelAttribute BatchUpdateCommand command) {
        catalogManager.batchUpdate(command);
    }
}

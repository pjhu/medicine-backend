package com.pjhu.medicine.catalog.adapter.resource.view;

import com.pjhu.medicine.catalog.application.service.BatchUpdateCommand;
import com.pjhu.medicine.catalog.application.service.CatalogManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.pjhu.medicine.identity.utils.Constant.ROOT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = ROOT + "/catalogs", produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogManager catalogManager;

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public void batchUpdateCatalog(@ModelAttribute BatchUpdateCommand command) {
        catalogManager.batchUpdate(command);
    }
}

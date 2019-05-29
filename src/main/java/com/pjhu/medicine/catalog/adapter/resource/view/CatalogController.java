package com.pjhu.medicine.catalog.adapter.resource.view;

import com.pjhu.medicine.catalog.application.service.command.BatchUpdateCommand;
import com.pjhu.medicine.catalog.application.service.CatalogApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.pjhu.medicine.identity.utils.Constant.ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = ADMIN + "/catalogs", produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogApplicationService catalogApplicationService;

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public void batchUpdateCatalog(@ModelAttribute BatchUpdateCommand command) {
        catalogApplicationService.batchUpdate(command);
    }
}

package com.pjhu.medicine.product.adapter.resource.view;

import com.pjhu.medicine.product.application.service.command.BatchUpdateCommand;
import com.pjhu.medicine.product.application.service.ProductApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.pjhu.medicine.common.utils.Constant.ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = ADMIN + "/products", produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ProductController {

    private final ProductApplicationService productApplicationService;

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public void batchUpdateProduct(@ModelAttribute BatchUpdateCommand command) {
        productApplicationService.batchUpdate(command);
    }
}

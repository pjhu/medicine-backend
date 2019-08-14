package com.pjhu.medicine.product.adapter.excel;

import com.pjhu.medicine.product.domain.model.Product;
import com.pjhu.medicine.product.domain.model.ProductParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Component(value = "productExcelParser")
public class ExcelProductParser implements ProductParser<MultipartFile> {

    @Override
    public List<Product> parse(MultipartFile input) {
        String fileName = input == null ? "NULL FILE" : input.getOriginalFilename();
        log.info("Begin parsing product excel file [{}].", fileName);
        ExcelParser parser = ExcelParser.getInstance(input);
        parser.parse();
        List<Product> itemData = parser.getProducts();
        log.info("End parsing product excel file [{}], [{}] data items returned.", fileName, itemData.size());
        return itemData;
    }
}

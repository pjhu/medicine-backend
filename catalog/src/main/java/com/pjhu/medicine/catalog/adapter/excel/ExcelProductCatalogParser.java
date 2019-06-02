package com.pjhu.medicine.catalog.adapter.excel;

import com.pjhu.medicine.catalog.domain.model.ItemDataParser;
import com.pjhu.medicine.catalog.domain.model.ItemData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component(value = "productCatalogExcelParser")
public class ExcelProductCatalogParser implements ItemDataParser<MultipartFile> {

    @Override
    public List<ItemData> parse(MultipartFile input) {
        String fileName = input == null ? "NULL FILE" : input.getOriginalFilename();
        log.info("Begin parsing catalog excel file [{}].", fileName);
        ExcelParser parser = ExcelParser.getInstance(input);
        parser.parse();
        ArrayList<ItemData> itemData = parser.getCatalogs();
        log.info("End parsing catalog excel file [{}], [{}] data items returned.", fileName, itemData.size());
        return itemData;
    }
}

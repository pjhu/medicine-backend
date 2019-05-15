package com.pjhu.medicine.common.excel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pjhu.medicine.catalog.domain.model.ItemData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class ExcelParser {

    private final Sheet sheet;
    private Map<String, ItemData> catalogs = Maps.newLinkedHashMap();

    private ExcelParser(MultipartFile input) {
        Workbook workbook = toWorkbook(input);
        this.sheet = workbook.getSheetAt(0);
    }

    public static ExcelParser getInstance(MultipartFile input){
        return new ExcelParser(input);
    }

    public void parse() {
        Iterator<Row> rowIterator = sheet.rowIterator();
        Row headerRow = rowIterator.next();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            log.debug("Processing row [{}]", row.getRowNum());
            Optional<ItemData> item = new ItemParser(row).parse();
            if (item.isPresent()) {
                catalogs.put(item.get().getSku(), item.get());
            } else {
                break;
            }
        }
    }

    public ArrayList<ItemData> getCatalogs() {
        return Lists.newArrayList(catalogs.values());
    }

    private Workbook toWorkbook(MultipartFile input) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(input.getInputStream());
        } catch (IOException e) {
            log.error("Read input file [{}] error", input.getName(), e);
        }
        return workbook;
    }

    private class ItemParser {
        private Row row;

        ItemParser(Row row) {
            this.row = row;
        }

        Optional<ItemData> parse() {
            String sku = getCellStrValue(0);
            String name = getCellStrValue(1);
            String description = getCellStrValue(2);
            String note = getCellStrValue(3);
            Long stock = getPrrValue(4);

            return Optional.of(ItemData.builder()
                    .sku(sku)
                    .name(name)
                    .description(description)
                    .note(note)
                    .stock(stock)
                    .build());
        }

        private String getCellStrValue(Integer index) {
            return getStringValue(this.row.getCell(index));
        }

        private long getPrrValue(Integer index) {
            return NumberUtils.toLong(getCellStrValue(index));
        }

    }

    private String getStringValue(Cell cell) {
        return Optional.ofNullable(cell)
                .map(new DataFormatter()::formatCellValue)
                .orElse(StringUtils.EMPTY);
    }
}

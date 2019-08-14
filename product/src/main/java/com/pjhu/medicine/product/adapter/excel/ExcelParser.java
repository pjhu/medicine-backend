package com.pjhu.medicine.product.adapter.excel;

import com.pjhu.medicine.common.domain.model.IdGenerator;
import com.pjhu.medicine.product.domain.model.Product;
import com.pjhu.medicine.product.domain.model.ProductSku;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
class ExcelParser {

    private final Sheet sheet;
    private List<Product> products;

    private ExcelParser(MultipartFile input) {
        this.products = new ArrayList<>();
        Workbook workbook = toWorkbook(input);
        this.sheet = workbook.getSheetAt(0);
    }

    static ExcelParser getInstance(MultipartFile input){
        return new ExcelParser(input);
    }

    void parse() {
        Iterator<Row> rowIterator = sheet.rowIterator();
        Row headerRow = rowIterator.next();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            log.debug("Processing row [{}]", row.getRowNum());
            Optional<Product> item = new ItemParser(row).parse();
            if (item.isPresent()) {
                Optional<Product> product = products.stream()
                        .filter(e -> e.getName().equals(item.get().getName())
                                && e.getBrand().equals(item.get().getBrand()))
                        .findFirst();

                if (product.isPresent()) {
                    product.map(e -> e.getSkus().addAll(item.get().getSkus()));
                } else {
                    products.add(item.get());
                }
            } else {
                break;
            }
        }
    }

    List<Product> getProducts() {
        return products;
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

        Optional<Product> parse() {
            String name = getCellStrValue(0);
            String brand = getCellStrValue(1);
            String description = getCellStrValue(2);
            String color = getCellStrValue(3);
            String price = getCellStrValue(4);
            Long stock = NumberUtils.toLong(getCellStrValue(5));

            ProductSku sku = ProductSku.builder()
                    .sku(String.valueOf(IdGenerator.nextIdentity()))
                    .color(color)
                    .price(Integer.valueOf(price))
                    .stock(stock)
                    .build();

            return Optional.of(Product.builder()
                    .name(name)
                    .brand(brand)
                    .description(description)
                    .skus(new ArrayList<>(Collections.singletonList(sku)))
                    .build());
        }

        private String getCellStrValue(Integer index) {
            return Optional.ofNullable(this.row.getCell(index))
                    .map(new DataFormatter()::formatCellValue)
                    .orElse(StringUtils.EMPTY);
        }
    }
}

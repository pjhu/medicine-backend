package com.pjhu.medicine.report.application.service;

import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportAssembler {

    private CsvMapper csvMapper = new CsvMapper();

    public ReportAssembler() {
        csvMapper.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    public byte[] assemble(List<?> items, Class<?> type) {
        if (null == items) {
            return new byte[0];
        }
        CsvSchema schema = csvMapper.schemaFor(type).withHeader();
        csvMapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
        return csvMapper.writer(schema).writeValueAsBytes(items);
    }
}

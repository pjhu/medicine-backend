package com.pjhu.medicine.product.application.service;

import com.pjhu.medicine.product.application.service.command.BatchUpdateCommand;
import com.pjhu.medicine.product.domain.model.Product;
import com.pjhu.medicine.product.domain.model.ProductParser;
import com.pjhu.medicine.product.domain.model.ProductRepository;
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
public class ProductApplicationService {

    private final ProductParser<MultipartFile> productParser;
    private final ProductRepository productRepository;

    @Transactional
    public void batchUpdate(BatchUpdateCommand command) {
        List<Product> parse = productParser.parse(command.getFile());

        List<Product> result = parse.stream()
                .peek(e -> {
                    List<Product> existingList = productRepository
                            .findAllByNameAndBrand(e.getName(), e.getBrand());
                    e.upsert(existingList);
                })
                .map(productRepository::save)
                .collect(Collectors.toList());
        log.info("Imported [{}] product from the excel.", result.size());
    }
}

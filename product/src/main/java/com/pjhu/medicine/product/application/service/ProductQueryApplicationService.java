package com.pjhu.medicine.product.application.service;

import com.pjhu.medicine.product.domain.model.Product;
import com.pjhu.medicine.product.domain.model.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class ProductQueryApplicationService {

    private final ProductRepository productRepository;

    public Optional<Product> getProductBy(Long id) {
        return productRepository.findById(id);
    }
}

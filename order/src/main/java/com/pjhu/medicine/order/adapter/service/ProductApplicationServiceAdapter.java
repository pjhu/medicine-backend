package com.pjhu.medicine.order.adapter.service;

import com.pjhu.medicine.product.application.service.ProductQueryApplicationService;
import com.pjhu.medicine.product.domain.model.Product;
import com.pjhu.medicine.product.domain.model.ProductSku;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductApplicationServiceAdapter {

    private final ProductQueryApplicationService productQueryApplicationService;

    public ProductDto getProductBy(Long id, String productSku) {
        Optional<Product> product = productQueryApplicationService.getProductBy(id);
        return product
                .map(e -> ProductDto.builder()
                        .price(e.getSkus().stream()
                                .filter(sku -> sku.getSku().equals(productSku))
                                .map(ProductSku::getPrice)
                                .findFirst().orElseThrow(RuntimeException::new))
                        .productName(e.getName())
                        .build())
                .orElse(ProductDto.builder().build());
    }
}

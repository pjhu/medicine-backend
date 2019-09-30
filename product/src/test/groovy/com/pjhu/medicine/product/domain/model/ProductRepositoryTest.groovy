package com.pjhu.medicine.product.domain.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@EnableAutoConfiguration
@ContextConfiguration(classes = ProductRepository.class)
@DataJpaTest
class ProductRepositoryTest extends Specification {

    @Autowired
    private ProductRepository repository

    void setup() {
        def productSku = ProductSku.builder()
                .sku("sku")
                .color("red")
                .price(10)
                .stock(100)
                .build()
        def product = Product.builder()
                .name("name")
                .brand("brand")
                .description("description")
                .productStatus(ProductStatus.ON_SALE)
                .skus(Arrays.asList(productSku))
                .build()
        repository.save(product)
    }

    def "product_entity_exist"() {
        expect:
        repository.findAll().size() == 1
    }

    def "findAllByNameAndBrand_is_available"() {
        when:
        def productList = repository.findAllByNameAndBrand("name", "brand")

        then:
        productList.size() == 1
    }
}

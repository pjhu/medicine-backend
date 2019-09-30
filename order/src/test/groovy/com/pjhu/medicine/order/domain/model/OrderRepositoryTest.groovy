package com.pjhu.medicine.order.domain.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@EnableAutoConfiguration
@ContextConfiguration(classes = OrderRepository.class)
@DataJpaTest
class OrderRepositoryTest extends Specification {

    @Autowired
    private OrderRepository repository

    void setup() {
        def orderProductDetail = OrderProductDetail.builder()
                .productSku("sku")
                .productName("product_name")
                .productPrice(10)
                .number(1)
                .build()
        def order = Order.builder()
                .productCount(1)
                .productAmountTotal(10)
                .orderAmountTotal(10)
                .orderProductDetails(Arrays.asList(orderProductDetail))
                .payChannel(PayChannel.ALI_PAY)
                .orderStatus(OrderStatus.UN_PAYED)
                .orderPaymentStatus(OrderPaymentStatus.UN_PAYED)
                .build()
        repository.save(order)
    }

    def "order_entity_exist" () {
        expect:
        def orderList = repository.findAll()
        orderList.size() == 1
    }
}

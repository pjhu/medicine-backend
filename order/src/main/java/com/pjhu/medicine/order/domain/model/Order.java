package com.pjhu.medicine.order.domain.model;

import com.pjhu.medicine.common.domain.model.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USER_ORDER")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractEntity {

    private Integer productCount;
    private Integer productAmountTotal;
    private Integer orderAmountTotal;

    @ElementCollection
    @CollectionTable(
            name = "order_product_detail",
            joinColumns = @JoinColumn(name = "order_id")
    )
    private List<OrderProductDetail> orderProductDetails;
    @Enumerated(value = EnumType.STRING)
    private PayChannel payChannel;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(value = EnumType.STRING)
    private OrderPaymentStatus orderPaymentStatus;

    @Builder
    public Order(Integer productCount, Integer productAmountTotal,
                 Integer orderAmountTotal, List<OrderProductDetail> orderProductDetails) {
        super();
        this.orderStatus = OrderStatus.UN_PAYED;
        this.orderPaymentStatus = OrderPaymentStatus.UN_PAYED;
        this.payChannel = PayChannel.ALI_PAY;
        this.productCount = productCount;
        this.productAmountTotal = productAmountTotal;
        this.orderAmountTotal = orderAmountTotal;
        this.orderProductDetails = orderProductDetails;
    }

    public Order placeOrder(Integer productCount, Integer productAmountTotal,
                            Integer orderAmountTotal, List<OrderProductDetail> orderProductDetails) {
        return new Order(productCount, productAmountTotal, orderAmountTotal, orderProductDetails);
    }
}

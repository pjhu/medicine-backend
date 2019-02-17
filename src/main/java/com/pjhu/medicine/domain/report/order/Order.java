package com.pjhu.medicine.domain.report.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.pjhu.medicine.domain.report.order.Order.*;

@Builder
@Getter
@JsonPropertyOrder(value = {COLUMN_NAME, COLUMN_QUANTITY, COLUMN_TOTAL_PRICE, COLUMN_CREATED_BY,
        COLUMN_CREATED_AT, COLUMN_LAST_MODIFIED_BY, COLUMN_LAST_MODIFIED_AT})
public class Order {

    static final String COLUMN_NAME = "name";
    static final String COLUMN_QUANTITY = "quantity";
    static final String COLUMN_TOTAL_PRICE = "totalPrice";
    static final String COLUMN_CREATED_BY = "createdBy";
    static final String COLUMN_CREATED_AT = "createdAt";
    static final String COLUMN_LAST_MODIFIED_BY = "lastModifiedBy";
    static final String COLUMN_LAST_MODIFIED_AT = "lastModifiedAt";

    private String name;
    private String quantity;
    private String totalPrice;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;
}

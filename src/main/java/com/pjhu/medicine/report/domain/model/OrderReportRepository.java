package com.pjhu.medicine.report.domain.model;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Order> findAllOrders() {
        String querySql = "SELECT name, quantity, total_price, created_at, created_by, " +
                "last_modified_at, last_modified_by " +
                "FROM user_order order by created_at desc";
        return jdbcTemplate.query(querySql, (rs, rowNum) -> Order.builder()
                .name(rs.getString("name"))
                .quantity(rs.getString("quantity"))
                .totalPrice(rs.getString("total_price"))
                .createdBy(rs.getString("created_by"))
                .createdAt(LocalDateTime.now())
                .lastModifiedBy(rs.getString("last_modified_by"))
                .lastModifiedAt(LocalDateTime.now())
                .build());
    }
}

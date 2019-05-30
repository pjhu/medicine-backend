package com.pjhu.medicine.order.adapter.persistence;

import com.pjhu.medicine.order.domain.model.Order;
import com.pjhu.medicine.order.domain.model.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepositoryAdapter implements OrderQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Order> getOrderBy(long id) {
        @SuppressWarnings("SqlNoDataSourceInspection")
        String querySql = "SELECT * FROM user_order WHERE id=?";
        RowMapper<Order> rowMapper = new BeanPropertyRowMapper<>(Order.class);
        Order order = jdbcTemplate.queryForObject(querySql, rowMapper, id);
        return Optional.ofNullable(order);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        @SuppressWarnings("SqlNoDataSourceInspection")
        String rowCountSql = "SELECT count(id) FROM user_order";
        @SuppressWarnings("ConstantConditions")
        long total = jdbcTemplate.queryForObject(rowCountSql, Long.class);
        @SuppressWarnings("SqlNoDataSourceInspection")
        String querySql = "SELECT * FROM user_order ORDER BY id DESC OFFSET ? LIMIT ?";
        RowMapper<Order> rowMapper = new BeanPropertyRowMapper<>(Order.class);
        List<Order> orders = jdbcTemplate.query(querySql, rowMapper,
                (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());
        return new PageImpl<>(orders, pageable, total);
    }
}

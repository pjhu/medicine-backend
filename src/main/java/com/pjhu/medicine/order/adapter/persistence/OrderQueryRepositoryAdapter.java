package com.pjhu.medicine.order.adapter.persistence;

import com.pjhu.medicine.order.domain.model.Order;
import com.pjhu.medicine.order.domain.model.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepositoryAdapter implements OrderQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Order> getOrderBy(long id) {
        String querySql = "SELECT * FROM user_order where id=?";
        RowMapper<Order> rowMapper = new BeanPropertyRowMapper<>(Order.class);
        Order order = jdbcTemplate.queryForObject(querySql, rowMapper, id);
        return Optional.ofNullable(order);
    }
}

package com.pjhu.medicine.order.domain.model;

import java.util.Optional;

public interface OrderQueryRepository {

    Optional<Order> getOrderBy(long id);
}

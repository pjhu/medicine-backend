package com.pjhu.medicine.report.domain.model;

import java.util.List;

public interface OrderReportRepository {

    List<Order> findAllOrders();
}

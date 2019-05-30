package com.pjhu.medicine.report.domain.model;

import java.util.List;

public interface ReportRepository {

    List<OrderReport> findAllOrders();
}

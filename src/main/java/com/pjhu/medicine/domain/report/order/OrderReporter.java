package com.pjhu.medicine.domain.report.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderReporter {

    private final OrderReportRepository orderReportRepository;

    @Transactional(readOnly = true)
    public List<Order> findAllOrders() {
        return orderReportRepository.findAllOrders();
    }
}

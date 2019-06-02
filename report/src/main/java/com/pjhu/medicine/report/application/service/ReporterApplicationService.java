package com.pjhu.medicine.report.application.service;

import com.pjhu.medicine.report.domain.model.OrderReport;
import com.pjhu.medicine.report.domain.model.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporterApplicationService {

    private final ReportRepository reportRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<OrderReport> findAllOrders() {
        return reportRepository.findAllOrders();
    }
}

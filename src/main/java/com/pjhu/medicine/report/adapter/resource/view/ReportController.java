package com.pjhu.medicine.report.adapter.resource.view;

import com.pjhu.medicine.report.adapter.resource.ResponseCreator;
import com.pjhu.medicine.report.application.service.ReportAssembler;
import com.pjhu.medicine.report.domain.model.Order;
import com.pjhu.medicine.report.application.service.OrderReporter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.pjhu.medicine.identity.utils.Constant.ROOT;

@RestController
@RequestMapping(value = ROOT + "/reports", method = RequestMethod.GET)
@RequiredArgsConstructor
public class ReportController {

    private final OrderReporter orderReporter;
    private final ReportAssembler reportAssembler;

    @GetMapping(value = "/orders")
    public ResponseEntity<byte[]> orders() {
        List<Order> allOrders = orderReporter.findAllOrders();
        byte[] data = reportAssembler.assemble(allOrders, Order.class);
        return ResponseCreator.createAttachmentResponse("order.csv", data);
    }
}

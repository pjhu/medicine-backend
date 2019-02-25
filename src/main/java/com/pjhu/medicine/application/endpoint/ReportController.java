package com.pjhu.medicine.application.endpoint;

import com.pjhu.medicine.application.ResponseCreator;
import com.pjhu.medicine.domain.report.ReportAssembler;
import com.pjhu.medicine.domain.report.order.Order;
import com.pjhu.medicine.domain.report.order.OrderReporter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.pjhu.medicine.infrastructure.common.Constant.ROOT;

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

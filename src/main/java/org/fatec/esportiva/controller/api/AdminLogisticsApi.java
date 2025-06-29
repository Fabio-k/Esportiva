package org.fatec.esportiva.controller.api;

import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.fatec.esportiva.dto.request.ActionRequest;
import org.fatec.esportiva.dto.request.OrderDto;
import org.fatec.esportiva.dto.request.TransactionDto;
import org.fatec.esportiva.exception.ApiException;
import org.fatec.esportiva.service.order.OrderService;
import org.fatec.esportiva.service.transaction.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
public class AdminLogisticsApi {

    private final TransactionService transactionService;

    private final OrderService orderService;

    @GetMapping("/admin/api/logistic")
    public ResponseEntity<List<TransactionDto>> getAllItems() {
        return ResponseEntity.ok().body(transactionService.getTransactionsAdmin());
    }

    @GetMapping("/admin/api/logistic/orders")
    public ResponseEntity<List<OrderDto>> getAllInReturnItems() {
        return ResponseEntity.ok().body(orderService.getOrders());
    }

    @PatchMapping("/admin/api/logistic/action")
    public ResponseEntity<Void> deliveryPipeline(@RequestBody ActionRequest req) {

        if (req.getTransactionId() != null) {
            transactionService.changeState(req.getTransactionId(), req.getIsApproved());
        } else if (req.getOrderId() != null) {
            orderService.changeState(req.getOrderId(), req.getIsApproved(), req.getIsReturnToStock());

        } else {
            throw new ApiException("Ao aprovar uma transação/ordem, ambos os ID ficaram nulos");
        }

        return ResponseEntity.ok().build();
    }
}

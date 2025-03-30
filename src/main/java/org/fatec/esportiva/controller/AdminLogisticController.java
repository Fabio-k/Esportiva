package org.fatec.esportiva.controller;

import java.util.List;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.request.TransactionDto;
import org.fatec.esportiva.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/logistic")
public class AdminLogisticController {
    private final TransactionService transactionService;

    @GetMapping("/in_processing")
    public String inProcessing(Model model) {
        List<TransactionDto> transactions = transactionService.getTransactions(OrderStatus.EM_PROCESSAMENTO);
        model.addAttribute("transactions", transactions);
        return "admin/logistic/in_processing";
    }

    @GetMapping("/in_transit")
    public String inTransit(Model model) {
        List<TransactionDto> transactions = transactionService.getTransactions(OrderStatus.EM_TRANSITO);
        model.addAttribute("transactions", transactions);
        return "admin/logistic/in_transit";
    }

    @GetMapping("/delivered")
    public String delivered(Model model) {
        List<TransactionDto> transactions = transactionService.getTransactions(OrderStatus.ENTREGUE);
        model.addAttribute("transactions", transactions);
        return "admin/logistic/delivered";
    }

    @GetMapping("/returning")
    public String returning(Model model) {
        List<TransactionDto> transactions = transactionService.getTransactions(OrderStatus.EM_TROCA);
        model.addAttribute("transactions", transactions);
        return "admin/logistic/returning";
    }

    @GetMapping("/returned")
    public String returned(Model model) {
        List<TransactionDto> transactions = transactionService.getTransactions(OrderStatus.TROCADO);
        model.addAttribute("transactions", transactions);
        return "admin/logistic/returned";
    }

}

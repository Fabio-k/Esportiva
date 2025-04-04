package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final TransactionService transactionService;

    @GetMapping
    public String getOrders(Model model){
        model.addAttribute("transactions", transactionService.getTransactions());
        return "/orders/index";
    }
}

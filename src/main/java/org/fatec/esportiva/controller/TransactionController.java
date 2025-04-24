package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public String getTransactions(Model model){
        model.addAttribute("transactions", transactionService.getTransactions());
        return "/orders/index";
    }

    @PatchMapping("/trade/{id}")
    public String requestTrade(@PathVariable Long id){
        transactionService.requestTrade(id);
        return "redirect:/transactions";
    }
}

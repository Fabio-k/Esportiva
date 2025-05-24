package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.response.TransactionResponseDto;
import org.fatec.esportiva.service.transaction.TransactionService;
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

    @GetMapping("/trade")
    public String getTransactionsTrade(Model model){
        model.addAttribute("transactions", transactionService.getTransactions());
        return "/orders/tradeIndex";
    }

    @PatchMapping("/trade/{id}")
    public String requestTrade(@PathVariable Long id, Model model){
        transactionService.requestTrade(id);
        TransactionResponseDto transaction = transactionService.getTransaction(id);
        model.addAttribute("transaction", transaction);
        return "redirect:/transactions/trade";
    }
    /*
    @PatchMapping("/trade/{id}")
    public ResponseEntity<String> requestTrade(@PathVariable Long id){
        transactionService.requestTrade(id);
        TransactionResponseDto transaction = transactionService.getTransaction(id);
        Context context = new Context();
        context.setVariable("transaction", transaction);
        String turboStream = templateEngine.process("orders/turbo/transactionCard", context);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "text/vnd.turbo-stream.html").body(turboStream);
    }
     */
}

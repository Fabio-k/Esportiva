package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.service.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;

    @GetMapping("/trade/{id}")
    public String trade(Model model, @PathVariable Long id){
        model.addAttribute("order", orderService.findByClientIdAndId(id));
        return "orders/trade";
    }

    @PatchMapping("/trade/{id}")
    public String patchTrade(@PathVariable Long id, @RequestParam(name = "tradeQuantity") Short quantity){
        if(quantity <= 0) throw new IllegalArgumentException("Quantidade a ser trocada deve ser maior do que 0");
        orderService.tradeOrder(id, quantity);
        return "redirect:/transactions/trade";
    }
}

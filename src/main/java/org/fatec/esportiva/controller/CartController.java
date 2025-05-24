package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.response.CartResponseDto;
import org.fatec.esportiva.service.ClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final ClientService clientService;

    @Value("${cart.product.timeoutInMinutes}")
    private Long timeoutInMinutes;

    @GetMapping
    public String getCart(Model model) throws Exception{
        CartResponseDto cartResponseDto = clientService.getCart();
        model.addAttribute("removedProducts", cartResponseDto.removedProducts());
        model.addAttribute("cartItems", cartResponseDto.items());
        model.addAttribute("timeoutInMinutes", timeoutInMinutes);
        return "cart";
    }
}

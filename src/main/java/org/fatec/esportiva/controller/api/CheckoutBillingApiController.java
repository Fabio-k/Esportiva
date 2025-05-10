package org.fatec.esportiva.controller.api;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.request.SplitCreditCardDto;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.service.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout/billing")
@SessionAttributes("checkoutSession")
@RequiredArgsConstructor
public class CheckoutBillingApiController {
    private final BillingService billingService;

    @PostMapping("/split-cards/save")
    public ResponseEntity<Map<String, String>> saveSplitCardsPayment(@RequestBody List<SplitCreditCardDto> creditCards,
                                                                     @ModelAttribute("checkoutSession") CheckoutSession checkoutSession){
        if(creditCards.size() < 2) return ResponseEntity.badRequest().body(Map.of("erro", "falta atributos"));
        billingService.saveSplitCardAmount(checkoutSession, creditCards);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}

package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.request.SplitCreditCardDto;
import org.fatec.esportiva.dto.request.SplitCreditCardForm;
import org.fatec.esportiva.dto.response.SplitCreditCardResponseDto;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.service.CheckoutService;
import org.fatec.esportiva.service.CreditCardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/checkout/billing")
@SessionAttributes("checkoutSession")
@RequiredArgsConstructor
public class CheckoutBillingController {
    private final CreditCardService creditCardService;
    private final CheckoutService checkoutService;

    @PostMapping("/split-cards/save")
    public String saveSplitCard(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, @ModelAttribute SplitCreditCardForm splitCreditCardForm, @AuthenticationPrincipal Client client, RedirectAttributes redirectAttributes){
        List<SplitCreditCardDto> creditCards = splitCreditCardForm.getCreditCards();
        if(creditCards.size() < 2) return "redirect:/checkout/billing";
        try {
            checkoutService.validateSplitPayment(checkoutSession, creditCards);
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/checkout/billing/split-cards";
        }
        List<SplitCreditCardResponseDto> splitCreditCardResponseDtos = creditCardService.getSplitCreditCardResponseDto(creditCards, client.getId());
        checkoutSession.getCreditCardPayments().addAll(splitCreditCardResponseDtos);

        return "redirect:/checkout/new";
    }
}

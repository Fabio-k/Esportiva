package org.fatec.esportiva.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.request.CreditCardDto;
import org.fatec.esportiva.dto.request.SplitCreditCardDto;
import org.fatec.esportiva.dto.request.SplitCreditCardForm;
import org.fatec.esportiva.dto.response.PromotionalCouponResponseDto;
import org.fatec.esportiva.dto.response.SplitCreditCardResponseDto;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.mapper.CreditCardMapper;
import org.fatec.esportiva.service.*;
import org.fatec.esportiva.validation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/checkout/billing")
@SessionAttributes("checkoutSession")
@RequiredArgsConstructor
public class CheckoutBillingController {
    private final CreditCardService creditCardService;
    private final CartEmptyValidator cartEmptyValidator;
    private final ClientService clientService;
    private final BillingService billingService;
    private final PromotionalCouponService promotionalCouponService;
    private final CurrencyService currencyService;
    private final CheckoutSessionService checkoutSessionService;

    @ModelAttribute
    public void addTotalSummary(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, Model model){
        BigDecimal totalPrice = checkoutSessionService.calculateTotalPrice(checkoutSession);
        BigDecimal totalExchangeVoucherPrice = checkoutSessionService.getExchangeVouchersTotalPrice(checkoutSession.getExchangeVoucherIds());
        BigDecimal cartTotalPrice = checkoutSessionService.getCartTotalPrice();

        model.addAttribute("exchangeVoucherTotalPrice", currencyService.format(totalExchangeVoucherPrice));
        model.addAttribute("productsTotalPrice", currencyService.format(cartTotalPrice));
        model.addAttribute("cartTotalPrice", currencyService.format(totalPrice));
    }

    @GetMapping
    public String getBilling(Model model, @ModelAttribute("checkoutSession") CheckoutSession checkoutSession){
        if(checkoutSession.getAddress() == null) return "redirect:/checkout/address";
        cartEmptyValidator.validate();
        List<CreditCardDto> creditCards = clientService.getClientCreditCards();

        PromotionalCouponResponseDto promotionalCouponResponseDto = promotionalCouponService.getPromotionalCouponOrReturnNull(checkoutSession.getPromotionalCouponCode());
        model.addAttribute("promotionalCoupon", promotionalCouponResponseDto);
        model.addAttribute("vouchers", clientService.getClientVouchers());
        model.addAttribute("creditCard", new CreditCardDto());
        model.addAttribute("creditCards", creditCards);

        return "checkout/billing/index";
    }

    @GetMapping("new")
    public String newBilling(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession,Model model){
        cartEmptyValidator.validate();
        if(checkoutSession.getAddress() == null) return "redirect:/checkout/address";

        model.addAttribute("creditCard", new CreditCardDto());
        return "checkout/billing/new";
    }

    @PostMapping("/credit_card/save")
    public String saveCreditCard(@Valid @ModelAttribute("creditCard") CreditCardDto dto, BindingResult result, @ModelAttribute("checkoutSession") CheckoutSession checkoutSession, @RequestParam(name = "saveCreditCard", required = false) boolean saveCreditCard, Model model){
        cartEmptyValidator.validate();
        if(result.hasErrors()){
            model.addAttribute("creditCard", dto);
            return "checkout/billing/new";
        }
        CreditCardDto savedCreditCard;

        savedCreditCard = creditCardService.createCreditCard(clientService.getAuthenticatedClient(), dto, saveCreditCard);

        checkoutSession.getCreditCardIds().add(savedCreditCard.getId());
        return "redirect:/checkout/billing";
    }

    @GetMapping("/split-cards")
    public String splitCards(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, Model model){
        List<Long> clientCreditCardsIds = checkoutSession.getCreditCardIds();
        if(clientCreditCardsIds.size() < 2) return "redirect:/checkout/billing";
        SplitCreditCardForm splitCreditCardForm = new SplitCreditCardForm();
        splitCreditCardForm.getCreditCards().addAll(creditCardService.findAllByIdAndClientId(clientCreditCardsIds, clientService.getAuthenticatedClient().getId()));
        model.addAttribute("creditCards", splitCreditCardForm);
        return "/checkout/billing/split-cards";
    }

    @PostMapping("/split-cards/save")
    public String saveSplitCard(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, @ModelAttribute SplitCreditCardForm splitCreditCardForm){
        List<SplitCreditCardDto> creditCards = splitCreditCardForm.getCreditCards();
        if(creditCards.size() < 2) return "redirect:/checkout/billing";
        billingService.saveSplitCardAmount(checkoutSession, creditCards);

        return "redirect:/checkout/new";
    }

    @PostMapping("/save")
    public String saveBilling(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession,
                              @RequestParam(name = "selectedCards", required = false) List<Long> creditCardsIds,
                              @RequestParam(name = "exchangeVouchers", required = false) List<Long> exchangeVoucherIds,
                              @RequestParam(name = "promotionalCouponCode", required = false) String promotionalCouponCode
    ){
        billingService.savePaymentMethods(checkoutSession, exchangeVoucherIds, creditCardsIds, promotionalCouponCode);

        if(checkoutSession.getCreditCardIds().size() > 1) return "redirect:/checkout/billing/split-cards";

        return "redirect:/checkout/new";
    }
}

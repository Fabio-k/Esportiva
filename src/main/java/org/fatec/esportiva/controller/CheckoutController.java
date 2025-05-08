package org.fatec.esportiva.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.response.AddressResponseDto;
import org.fatec.esportiva.entity.address.Address;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.mapper.AddressMapper;
import org.fatec.esportiva.mapper.CartItemMapper;
import org.fatec.esportiva.dto.request.AddressDto;
import org.fatec.esportiva.dto.request.CreditCardDto;
import org.fatec.esportiva.dto.response.CartItemResponseDto;
import org.fatec.esportiva.dto.response.PromotionalCouponResponseDto;
import org.fatec.esportiva.service.*;
import org.fatec.esportiva.validation.CartEmptyValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/checkout")
@SessionAttributes("checkoutSession")
@RequiredArgsConstructor
public class CheckoutController {
    private final ClientService clientService;
    private final AddressService addressService;
    private final CurrencyService currencyService;
    private final CheckoutService checkoutService;
    private final FreightService freightService;
    private final CartEmptyValidator cartEmptyValidator;
    private final CheckoutSessionService checkoutSessionService;
    private final PromotionalCouponService promotionalCouponService;

    @ModelAttribute("checkoutSession")
    public CheckoutSession createSession(){
        return new CheckoutSession();
    }

    @ModelAttribute
    public void addTotalSummary(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, Model model){
        BigDecimal totalPrice = checkoutSessionService.calculateTotalPrice(checkoutSession);
        BigDecimal totalExchangeVoucherPrice = checkoutSessionService.getExchangeVouchersTotalPrice(checkoutSession.getExchangeVoucherIds());
        BigDecimal cartTotalPrice = checkoutSessionService.getCartTotalPrice();

        model.addAttribute("exchangeVoucherTotalPrice", currencyService.format(totalExchangeVoucherPrice));
        model.addAttribute("productsTotalPrice", currencyService.format(cartTotalPrice));
        model.addAttribute("cartTotalPrice", currencyService.format(totalPrice));
    }

    @GetMapping("/address")
    public String checkoutAddress(Model model, @ModelAttribute("checkoutSession") CheckoutSession checkoutSession){
        cartEmptyValidator.validate();
        checkoutSession.getCreditCardPayments().clear();
        checkoutSession.getCreditCardIds().clear();
        model.addAttribute("addresses", clientService.getClientAddresses());
        model.addAttribute("address", new AddressDto());
        return "checkout/address/index";
    }

    @GetMapping("/address/new")
    public String newCheckoutAddress(Model model){
        cartEmptyValidator.validate();
        if(!model.containsAttribute("address")){
            model.addAttribute("address", new AddressDto());
        }
        return  "checkout/address/new";
    }

    @PostMapping("/address/save")
    public String saveAddress(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, @RequestParam(name = "selectedAddress") Long id){
        cartEmptyValidator.validate();
        Address address = addressService.findById(id);
        BigDecimal freight = freightService.calculate(address.getCep().getState(), clientService.getCart().items());
        checkoutSession.setAddress(AddressMapper.toAddressDtoResponse(addressService.findById(address.getId()), freight, currencyService.format(freight)));
        return "redirect:/checkout/billing";
    }

    @PostMapping("/address/new_address/save")
    public String saveNewAddress(@Valid @ModelAttribute("address")AddressDto dto, BindingResult result, @ModelAttribute("checkoutSession") CheckoutSession checkoutSession, Model model, @RequestParam(name = "saveAddress", required = false) boolean saveAddress){
        cartEmptyValidator.validate();
        if (result.hasErrors()) {
            model.addAttribute("address", dto);
            return "checkout/address/new";
        }
        AddressDto address = addressService.createAddress(dto, saveAddress, clientService.getAuthenticatedClient());
        BigDecimal freight = freightService.calculate(address.getState(), clientService.getCart().items());

        checkoutSession.setAddress(AddressMapper.toAddressDtoResponse(addressService.findById(address.getId()), freight, currencyService.format(freight)));
        return "redirect:/checkout/address";
    }

    @GetMapping("/new")
    public String newCheckout(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, Model model){
        if(checkoutSession.getAddress() == null) return "redirect:/checkout/address";

        cartEmptyValidator.validate();
        PromotionalCouponResponseDto promotionalCouponResponseDto = promotionalCouponService.getPromotionalCouponOrReturnNull(checkoutSession.getPromotionalCouponCode());

        model.addAttribute("address", checkoutSession.getAddress());
        model.addAttribute("promotionalCoupon", promotionalCouponResponseDto);
        model.addAttribute("creditCards", checkoutSessionService.getCreditCardsDto(checkoutSession));
        model.addAttribute("items", clientService.getClientCartItems());
        model.addAttribute("creditCardPayment", checkoutSession.getCreditCardPayments());

        return "checkout/new";
    }

    @PostMapping("/save")
    public String buyCart(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, Model model){
        if(checkoutSession.getAddress() == null) return "redirect:/checkout/address";
        model.addAttribute("items", clientService.getClientCartItems());
        model.addAttribute("address", checkoutSession.getAddress());
        checkoutService.processCheckout(checkoutSession, model);

        return "/checkout/result";
    }

}

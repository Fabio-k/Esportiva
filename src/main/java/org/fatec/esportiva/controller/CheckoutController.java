package org.fatec.esportiva.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.*;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.mapper.AddressMapper;
import org.fatec.esportiva.mapper.CartItemMapper;
import org.fatec.esportiva.mapper.CreditCardMapper;
import org.fatec.esportiva.dto.request.AddressDto;
import org.fatec.esportiva.dto.request.CreditCardDto;
import org.fatec.esportiva.dto.response.AddressResponseDto;
import org.fatec.esportiva.dto.response.CartItemResponseDto;
import org.fatec.esportiva.dto.response.PromotionalCouponResponseDto;
import org.fatec.esportiva.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/checkout")
@SessionAttributes("checkoutSession")
@RequiredArgsConstructor
public class CheckoutController {
    private final ClientService clientService;
    private final AddressService addressService;
    private final CreditCardService creditCardService;
    private final TransactionService transactionService;
    private final ExchangeVoucherService exchangeVoucherService;
    private final CurrencyService currencyService;
    private final CheckoutService checkoutService;
    private final PromotionalCouponService promotionalCouponService;
    private final FreightService freightService;
    private final CartService cartService;

    @ModelAttribute("checkoutSession")
    public CheckoutSession createSession(){
        return new CheckoutSession();
    }

    private Client client(){
        return clientService.getAuthenticatedClient();
    }

    private Boolean isCartEmpty(){
        return client().getCart().getCartItems().isEmpty();
    }

    @ModelAttribute("cartTotalPrice")
    public String totalPrice(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession){
        return currencyService.format(checkoutService.calculateTotalPrice(checkoutSession));
    }

    @ModelAttribute("exchangeVoucherTotalPrice")
    public String totalExchangeVoucherPrice(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession){
        return currencyService.format(checkoutService.getExchangeVouchersTotalPrice(checkoutSession));
    }

    @ModelAttribute("productsTotalPrice")
    public String productsTotalPrice(){
        return currencyService.format(checkoutService.getCartTotalPrice());
    }

    @GetMapping("/address")
    public String checkoutAddress(Model model){
        if(isCartEmpty()) return "redirect:/cart";
        model.addAttribute("addresses", clientService.getClientAddresses());
        model.addAttribute("address", new AddressDto());
        return "checkout/address/index";
    }

    @GetMapping("/address/new")
    public String newCheckoutAddress(Model model){
        if(isCartEmpty()) return "redirect:/cart";
        if(!model.containsAttribute("address")){
            model.addAttribute("address", new AddressDto());
        }
        return  "checkout/address/new";
    }

    @PostMapping("/address/save")
    public String saveAddress(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, @RequestParam(name = "selectedAddress") Long id){
        if(isCartEmpty()) return "redirect:/cart";
        Address address = addressService.findById(id);
        BigDecimal freight = freightService.calculate(address.getCep().getState(), clientService.getCart().items());
        checkoutSession.setAddress(AddressMapper.toAddressDtoResponse(addressService.findById(address.getId()), freight, currencyService.format(freight)));
        return "redirect:/checkout/billing";
    }

    @PostMapping("/address/new_address/save")
    public String saveNewAddress(@Valid @ModelAttribute("address")AddressDto dto, BindingResult result, @ModelAttribute("checkoutSession") CheckoutSession checkoutSession, Model model, @RequestParam(name = "saveAddress", required = false) boolean saveAddress){
        if(isCartEmpty()) return "redirect:/cart";
        if (result.hasErrors()) {
            model.addAttribute("address", dto);
            return "checkout/address/new";
        }
        AddressDto address = addressService.createAddress(dto, saveAddress, client());
        BigDecimal freight = freightService.calculate(address.getState(), clientService.getCart().items());

        checkoutSession.setAddress(AddressMapper.toAddressDtoResponse(addressService.findById(address.getId()), freight, currencyService.format(freight)));
        return "redirect:/checkout/address";
    }

    @GetMapping("/billing")
    public String getBilling(Model model, @ModelAttribute("checkoutSession") CheckoutSession checkoutSession){
        if(isCartEmpty()) return "redirect:/cart";
        if(checkoutSession.getAddress() == null) return "redirect:/checkout/address";
        List <CreditCardDto> creditCards = client().getCreditCards().stream()
                .map(CreditCardMapper::toCreditCardDto).toList();
        List<CreditCardDto> allCreditCards = new ArrayList<>(creditCards);

        if (checkoutSession.getCreditCardIds() != null) {
            Set<Long> uniqueCreditCards = creditCards.stream().map(CreditCardDto::getId).collect(Collectors.toSet());

            checkoutSession.getCreditCardIds().stream()
                    .filter(uniqueCreditCards::add)
                    .map(creditCardService::findCreditCard)
                    .map(CreditCardMapper::toCreditCardDto)
                    .forEach(allCreditCards::add);
        }
        PromotionalCouponResponseDto promotionalCouponResponseDto = promotionalCouponService.getPromotionalCouponOrReturnNull(checkoutSession.getPromotionalCouponCode());
        model.addAttribute("promotionalCoupon", promotionalCouponResponseDto);
        model.addAttribute("vouchers", clientService.getClientVouchers());
        model.addAttribute("creditCard", new CreditCardDto());
        model.addAttribute("creditCards", allCreditCards);
        return "checkout/billing/index";
    }

    @GetMapping("/billing/new")
    public String newBilling(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession,Model model){
        if(isCartEmpty()) return "redirect:/cart";
        if(checkoutSession.getAddress() == null){
            return "redirect:/checkout/address";
        }
        model.addAttribute("creditCard", new CreditCardDto());
        return "checkout/billing/new";
    }

    @PostMapping("/billing/save")
    public String saveBilling(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession,
                              @RequestParam(name = "selectedCards", required = false) List<Long> creditCardsIds,
                              @RequestParam(name = "exchangeVouchers", required = false) List<Long> exchangeVoucherIds,
                              @RequestParam(name = "promotionalCouponCode", required = false) String promotionalCouponCode,
                              RedirectAttributes redirectAttributes
    ){
        if(isCartEmpty()) return "redirect:/cart";
        if(exchangeVoucherIds != null){
            exchangeVoucherService.validateExchangeVoucherOwnership(exchangeVoucherIds, client().getId());
            checkoutSession.setExchangeVoucherIds(exchangeVoucherIds);
        } else checkoutSession.getExchangeVoucherIds().clear();

        PromotionalCouponResponseDto promotionalCoupon = promotionalCouponService.getPromotionalCouponOrReturnNull(promotionalCouponCode);
        if(promotionalCoupon != null) {
            checkoutSession.setPromotionalCouponCode(promotionalCoupon.code());
        }

        if(creditCardsIds != null)
            checkoutSession.setCreditCardIds(creditCardsIds);
        else
            checkoutSession.getCreditCardIds().clear();

        try {
            checkoutService.validateEachCartHasMinimumPaymentOfTenOnOnlyCreditCardPayment(checkoutSession);
            checkoutService.validateCreditCardsCannotBeUsedWhenTotalPriceIsZero(checkoutSession);
            checkoutService.validateUnusedExchangeVouchers(checkoutSession);
            checkoutService.validateInsufficientPayment(checkoutSession);
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/checkout/billing";
        }

        return "redirect:/checkout/new";
    }

    @PostMapping("/billing/credit_card/save")
    public String saveCreditCard(@Valid @ModelAttribute("creditCard") CreditCardDto dto, BindingResult result, @ModelAttribute("checkoutSession") CheckoutSession checkoutSession, @RequestParam(name = "saveCreditCard", required = false) boolean saveCreditCard, Model model){
        if(isCartEmpty()) return "redirect:/cart";
        if(result.hasErrors()){
            model.addAttribute("creditCard", dto);
            return "checkout/billing/new";
        }
        CreditCardDto savedCreditCard;

        if (saveCreditCard){
            savedCreditCard = creditCardService.createCreditCard(clientService.getAuthenticatedClient(), dto);
        } else {
            savedCreditCard = creditCardService.saveCreditCard(dto);
        }

        checkoutSession.getCreditCardIds().add(savedCreditCard.getId());
        return "redirect:/checkout/billing";
    }

    @GetMapping("/new")
    public String newCheckout(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, Model model){
        List<CartItemResponseDto> cartItems = client().getCart().getCartItems()
                .stream().map(CartItemMapper::toCartItemResponseDto).toList();
        if(cartItems.isEmpty()) return "redirect:/cart";

        if(checkoutSession.getAddress() == null) return "redirect:/checkout/address";

        model.addAttribute("address", checkoutSession.getAddress());

        PromotionalCouponResponseDto promotionalCouponResponseDto = promotionalCouponService.getPromotionalCouponOrReturnNull(checkoutSession.getPromotionalCouponCode());
        model.addAttribute("promotionalCoupon", promotionalCouponResponseDto);

        List<CreditCardDto> creditCards = checkoutService.getCreditCardsDto(checkoutSession);
        model.addAttribute("creditCards", creditCards);

        model.addAttribute("items", cartItems);

        return "checkout/new";
    }

    @PostMapping("/save")
    public String buyCart(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, Model model){
        if(checkoutSession.getAddress() == null) return "redirect:/checkout/address";
        List<CartItemResponseDto> items = clientService.getCart().items();
        AddressResponseDto address = checkoutSession.getAddress();

        Transaction transaction = transactionService.generateTransaction(checkoutSession);
        try{
            checkoutService.validatePayment(checkoutSession);
        } catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            transactionService.denyTransaction(transaction);
        }

        checkoutService.generateExchangeVoucher(checkoutSession);
        cartService.cleanCart();

        model.addAttribute("items", items);
        model.addAttribute("address", address);
        return "/checkout/result";
    }

}

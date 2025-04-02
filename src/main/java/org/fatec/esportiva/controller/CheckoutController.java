package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.*;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.mapper.CartItemMapper;
import org.fatec.esportiva.mapper.CreditCardMapper;
import org.fatec.esportiva.request.AddressDto;
import org.fatec.esportiva.request.CreditCardDto;
import org.fatec.esportiva.response.CartItemResponseDto;
import org.fatec.esportiva.response.CartResponseDto;
import org.fatec.esportiva.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final CartService cartService;

    @ModelAttribute("checkoutSession")
    public CheckoutSession createSession(){
        return new CheckoutSession();
    }

    @ModelAttribute("cartTotalPrice")
    public String totalPrice(){
        CartResponseDto cart = cartService.getCart();
        return cart.getTotalPrice();
    }

    @GetMapping("/address")
    public String checkoutAddress(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, Model model){
        model.addAttribute("addresses", clientService.getAuthenticatedClient().getAddresses());
        model.addAttribute("address", new AddressDto());
        return "checkout/address/index";
    }

    @GetMapping("/address/new")
    public String newChceckoutAddress(Model model){
        if(!model.containsAttribute("address")){
            model.addAttribute("address", new AddressDto());
        }
        return  "checkout/address/new";
    }

    @PostMapping("/address/save")
    public String saveAddress(@ModelAttribute("addresss")AddressDto address, @ModelAttribute("checkoutSession") CheckoutSession checkoutSession){
        Client client = clientService.getAuthenticatedClient();
        if(address.getId() == null){
            address = (AddressDto) addressService.updateOrCreateAddress(client, List.of(address));
        }
        checkoutSession.setAddressId(address.getId());
        return "redirect:/checkout/billing";
    }

    @GetMapping("/billing")
    public String getBilling(Model model, @ModelAttribute("checkoutSession") CheckoutSession checkoutSession){
        if(checkoutSession.getAddressId() == null) return "redirect:/checkout/address";
        Client client =  clientService.getAuthenticatedClient();
        List <CreditCardDto> creditCards = client.getCreditCards().stream()
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

        model.addAttribute("creditCard", new CreditCardDto());
        model.addAttribute("creditCards", allCreditCards);
        return "checkout/billing/index";
    }

    @GetMapping("/billing/new")
    public String newBilling(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession,Model model){
        if(checkoutSession.getAddressId() == null){
            return "redirect:/checkout/address";
        }
        model.addAttribute("creditCard", new CreditCardDto());
        return "checkout/billing/new";
    }

    @PostMapping("/billing/save")
    public String saveBilling(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, @RequestParam(name = "selectedCards", required = false) List<Long> creditCardsIds){
        if(creditCardsIds == null) return "redirect:/checkout/billing";
        checkoutSession.setCreditCardIds(creditCardsIds);
        return "redirect:/checkout/new";
    }

    @PostMapping("/billing/credit_card/save")
    public String saveCreditCard(@ModelAttribute("creditCard") CreditCardDto dto, @ModelAttribute("checkoutSession") CheckoutSession checkoutSession, @RequestParam(name = "saveCreditCard", required = false) boolean saveCreditCard){
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
        List<CartItemResponseDto> cartItems = clientService.getAuthenticatedClient().getCart().getCartItems()
                .stream().map(CartItemMapper::toCartItemResponseDto).toList();
        if(cartItems.isEmpty()) return "redirect:/cart";

        if(checkoutSession.getAddressId() == null) return "redirect:/checkout/address";

        if (checkoutSession.getCreditCardIds() == null || checkoutSession.getCreditCardIds().isEmpty()) return "redirect:/checkout/billing";

        Address address = addressService.findById(checkoutSession.getAddressId());
        model.addAttribute("address", address);

        List<CreditCardDto> creditCards = checkoutSession.getCreditCardIds().stream()
                .map(creditCardId -> {
                    CreditCard creditCard = creditCardService.findCreditCard(creditCardId);
                    return CreditCardMapper.toCreditCardDto(creditCard);
                })
                .toList();
        model.addAttribute("creditCards", creditCards);

        model.addAttribute("items", cartItems);

        return "checkout/new";
    }

    @PostMapping("/save")
    public String buyCart(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession){
        if(checkoutSession.getAddressId() == null) return "redirect:/checkout/address";

        if (checkoutSession.getCreditCardIds().isEmpty()) return "redirect:/checkout/billing";

        transactionService.generateTransaction(checkoutSession);


        return "redirect:/";
    }

}

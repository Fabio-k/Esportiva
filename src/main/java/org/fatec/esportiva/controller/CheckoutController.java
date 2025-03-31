package org.fatec.esportiva.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Address;
import org.fatec.esportiva.entity.CartItem;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.CreditCard;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.request.AddressDto;
import org.fatec.esportiva.request.CreditCardDto;
import org.fatec.esportiva.service.AddressService;
import org.fatec.esportiva.service.ClientService;
import org.fatec.esportiva.service.CreditCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/checkout")
@SessionAttributes("checkoutSession")
@RequiredArgsConstructor
public class CheckoutController {
    private final ClientService clientService;
    private final AddressService addressService;
    private final CreditCardService creditCardService;

    @ModelAttribute("checkoutSession")
    public CheckoutSession createSession(){
        return new CheckoutSession();
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
        List <CreditCard> creditCards = clientService.getAuthenticatedClient().getCreditCards();
        if(checkoutSession.getAddressId() == null) return "redirect:/checkout/address";
        model.addAttribute("creditCard", new CreditCardDto());
        model.addAttribute("creditCards", creditCards);
        return "checkout/billing/index";
    }

    @GetMapping("/billing/new")
    public String newBilling(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession,Model model){
        if(checkoutSession.getAddressId() == null){
            return "redirect:/checkout/address";
        }
        if (checkoutSession.getCreditCardIds().isEmpty()){
            return "redirect:/checkout/billing";
        }
        return "checkout/billing/new";
    }

    @PostMapping("/billing/save")
    public String saveBilling(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, @RequestParam(name = "selectedCards", required = false) List<Long> creditCardsIds){
        checkoutSession.setCreditCardIds(creditCardsIds);
        return "redirect:/checkout/new";
    }

    @GetMapping("/new")
    public String newCheckout(@ModelAttribute("checkoutSession") CheckoutSession checkoutSession, Model model){
        List<CartItem> cartItems = clientService.getAuthenticatedClient().getCart().getCartItems();
        if(cartItems.isEmpty()) return "redirect:/cart";

        if(checkoutSession.getAddressId() == null) return "redirect:/checkout/address";

        if (checkoutSession.getCreditCardIds().isEmpty()) return "redirect:/checkout/billing";

        Address address = addressService.findById(checkoutSession.getAddressId());
        model.addAttribute("address", address);

        List<CreditCard> creditCards = checkoutSession.getCreditCardIds().stream().map(creditCardService::findCreditCard).toList();
        model.addAttribute("creditCards", creditCards);

        model.addAttribute("items", cartItems);



        return "checkout/new";
    }

}

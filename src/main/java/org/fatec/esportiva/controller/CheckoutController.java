package org.fatec.esportiva.controller;

import jakarta.validation.Valid;
import org.fatec.esportiva.request.AddressDto;
import org.fatec.esportiva.request.CreditCardDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @GetMapping("/address")
    public String checkoutAddress(){
        return "checkout/address/index";
    }

    @GetMapping("/address/new")
    public String newChceckoutAddress(Model model){
        if(!model.containsAttribute("address")){
            model.addAttribute("address", new AddressDto());
        }
        return  "checkout/address/new";
    }

    @GetMapping("/billing")
    public String getBilling(Model model){
        return "checkout/billing/index";
    }

    @GetMapping("/billing/new")
    public String newBilling(Model model){
        model.addAttribute("creditCard", new CreditCardDto());
        return "checkout/billing/new";
    }

    @PostMapping("/billing/save")
    public String saveBilling(Model model){
        return "checkout/new";
    }

    @PostMapping("/address/save")
    public String saveAddress(@Valid @ModelAttribute("address") AddressDto address, BindingResult result, @RequestParam(value = "save", required = false) boolean saveAddress) {
        /*
        if(result.hasErrors()){
            return "checkout/address/new";
        }
         */
        return "redirect:/checkout/billing";
    }
}

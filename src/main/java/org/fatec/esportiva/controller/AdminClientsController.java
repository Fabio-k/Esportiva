package org.fatec.esportiva.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.mapper.AddressMapper;
import org.fatec.esportiva.mapper.ClientMapper;
import org.fatec.esportiva.mapper.CreditCardMapper;
import org.fatec.esportiva.request.AddressDto;
import org.fatec.esportiva.request.ClientDto;
import org.fatec.esportiva.request.CreditCardDto;
import org.fatec.esportiva.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/clients")
public class AdminClientsController {
    private final ClientService clientService;

    @GetMapping
    public String home(Model model, RedirectAttributes redirectAttributes) throws Exception{
        List<Client> clients = clientService.getClients();
        model.addAttribute("users", clients);
        return "admin/clients/index";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("formAction", "/admin/clients/save");
        if (!model.containsAttribute("user")) {
            ClientDto clientDto = new ClientDto();
            clientDto.getAddresses().add(new AddressDto());
            clientDto.getCreditCards().add(new CreditCardDto());
            model.addAttribute("user", clientDto);
        }
        return "admin/clients/new";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") ClientDto clientDto, BindingResult result, Model model){
        if (result.hasErrors()){
            return "admin/clients/new";
        }
        clientService.save(clientDto);
        return "redirect:/admin/clients";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
       Client client = clientService.findClient(id);
        clientService.deleteClient(client);
        return "redirect:/admin/clients";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        if(!model.containsAttribute("user")){
            Client user = clientService.findClient(id);
            ClientDto userDto = ClientMapper.toUserDto(user);
            userDto.setAddresses(AddressMapper.toAddressDtoList(user.getAddresses()));
            userDto.setCreditCards(user.getCreditCards().stream().map(CreditCardMapper::toCreditCardDto).toList());
            model.addAttribute("user", userDto);
        }
        model.addAttribute("id", id);
        model.addAttribute("formAction", "/admin/clients/update/" + id);
        return "/admin/clients/edit";
    }

    @PatchMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("user") ClientDto userDto, BindingResult result, Model model){
        if(result.hasErrors()){
            return "admin/clients/edit";
        }
        clientService.update(id, userDto);
        return "redirect:/admin/clients";
    }
}

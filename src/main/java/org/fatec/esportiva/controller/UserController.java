package org.fatec.esportiva.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.mapper.AddressMapper;
import org.fatec.esportiva.mapper.ClientMapper;
import org.fatec.esportiva.mapper.CreditCardMapper;
import org.fatec.esportiva.request.AddressDto;
import org.fatec.esportiva.request.ClientDto;
import org.fatec.esportiva.request.CreditCardDto;
import org.fatec.esportiva.request.UserLoginRequest;
import org.fatec.esportiva.service.AddressService;
import org.fatec.esportiva.service.AuthService;
import org.fatec.esportiva.service.ClientService;
import org.fatec.esportiva.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ClientService clientService;
    private final AuthService authService;
    private final AddressService addressService;

    @GetMapping
    public String getUsers(Model model){
        List<UserDetails> users = userService.getUsers();
        model.addAttribute("user", new UserLoginRequest());
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("formAction", "/users/save");
        if (!model.containsAttribute("user")) {
            ClientDto clientDto = new ClientDto();
            clientDto.getAddresses().add(new AddressDto());
            clientDto.getCreditCards().add(new CreditCardDto());
            model.addAttribute("user", clientDto);
        }
        return "users/new";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") ClientDto clientDto, BindingResult result, Model model){
        if (result.hasErrors()){
            return "users/edit";
        }
        clientService.save(clientDto);
        return "redirect:/dashboard";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginRequest userLoginRequest, HttpServletRequest request, RedirectAttributes redirectAttributes){
        UserDetails user = userService.findByEmail(userLoginRequest.getEmail()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        authService.authenticateUser(user, request);
        redirectAttributes.addFlashAttribute("mensagem", "usuário cadastrado com sucesso!");
        return "redirect:/dashboard";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
       Client client = clientService.findClient(id);
        clientService.deleteClient(client);
        return "redirect:/dashboard";
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
        model.addAttribute("formAction", "/users/update/" + id);
        return "users/edit";
    }

    @PatchMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("user") ClientDto userDto, BindingResult result, Model model){
        if(result.hasErrors()){
            return "users/edit";
        }
        clientService.update(id, userDto);
        return "redirect:/dashboard";
    }
}

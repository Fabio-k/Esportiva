package org.fatec.esportiva.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.mapper.AddressMapper;
import org.fatec.esportiva.mapper.ClientMapper;
import org.fatec.esportiva.entity.Clients;
import org.fatec.esportiva.repository.UserRepository;
import org.fatec.esportiva.request.AddressDto;
import org.fatec.esportiva.request.ClientDto;
import org.fatec.esportiva.request.UserLoginRequest;
import org.fatec.esportiva.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping
    public String getUsers(Model model) {
        List<Clients> users = userRepository.findAll();
        model.addAttribute("user", new UserLoginRequest());
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("formAction", "/users/save");
        if (!model.containsAttribute("user")) {
            ClientDto userDto = new ClientDto();
            userDto.getAddresses().add(new AddressDto());
            model.addAttribute("user", userDto);
        }
        model.addAttribute("body", "users/new.html :: content");
        return "layout";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") ClientDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("body", "users/new.html :: content");
            return "layout";
        }
        Clients user = ClientMapper.toUser(userDto);
        user.setAddresses(AddressMapper.toAddressList(user, userDto.getAddresses()));
        userService.save(user);
        return "redirect:/dashboard";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginRequest userLoginRequest, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        Clients user = userService.findUser(userLoginRequest.getId());
        authenticateUser(user, request);
        redirectAttributes.addFlashAttribute("mensagem", "usuário cadastrado com sucesso!");
        return "redirect:/dashboard";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Clients user = userService.findUser(id);
        userService.deleteUser(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("user")) {
            Clients user = userService.findUser(id);
            ClientDto userDto = ClientMapper.toUserDto(user);
            userDto.setAddresses(AddressMapper.toAddressDtoList(user.getAddresses()));
            model.addAttribute("user", userDto);
        }
        model.addAttribute("id", id);
        model.addAttribute("formAction", "/users/update/" + id);
        model.addAttribute("body", "users/edit.html :: content");
        return "layout";
    }

    @PatchMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("user") ClientDto userDto,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("body", "users/edit.html :: content");
            return "layout";
        }
        userService.update(id, userDto);
        return "redirect:/dashboard";
    }

    private void authenticateUser(Clients user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }
}

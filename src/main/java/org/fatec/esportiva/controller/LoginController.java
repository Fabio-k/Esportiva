package org.fatec.esportiva.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.request.UserLoginRequest;
import org.fatec.esportiva.service.AuthService;
import org.fatec.esportiva.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping
    public String getUsers(Model model){
        List<UserDetails> users = userService.getUsers();
        model.addAttribute("user", new UserLoginRequest());
        model.addAttribute("users", users);
        return "login";
    }

    @PostMapping("/auth")
    public String login(@ModelAttribute UserLoginRequest userLoginRequest, HttpServletRequest request, BindingResult result){
        if (result.hasErrors()){
            return "login";
        }
        UserDetails user = userService.findByEmail(userLoginRequest.getEmail()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        authService.authenticateUser(user, request);
        return "redirect:/admin/clients";
    }
}

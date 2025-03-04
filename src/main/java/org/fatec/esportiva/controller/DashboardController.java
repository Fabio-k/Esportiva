package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.service.AuthService;
import org.fatec.esportiva.service.ClientService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final ClientService userService;
    private final AuthService authService;

    @GetMapping
    public String home(Model model) throws Exception{
        UserDetails user = authService.getAuthenticatedUser();
        List<Client> clients = userService.getClients();
        model.addAttribute("currentUser", user);
        model.addAttribute("users", clients);
        model.addAttribute("body","dashboard.html :: content");
        return "layout";
    }
}

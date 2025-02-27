package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.model.User;
import org.fatec.esportiva.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final UserService userService;

    @GetMapping
    public String home(Model model) throws Exception{
        User user = userService.getAuthenticatedUser();
        List<User> users = userService.getUsers();
        model.addAttribute("currentUser", user);
        model.addAttribute("users", users);
        model.addAttribute("body","dashboard.html :: content");
        return "layout";
    }
}

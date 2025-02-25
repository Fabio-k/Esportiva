package org.fatec.esportiva.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.mapper.UserMapper;
import org.fatec.esportiva.model.User;
import org.fatec.esportiva.repository.UserRepository;
import org.fatec.esportiva.request.UserLoginRequest;
import org.fatec.esportiva.response.UserResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @GetMapping
    public String getUsers(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("user", new UserLoginRequest());
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginRequest userLoginRequest, HttpServletRequest request, RedirectAttributes redirectAttributes){
        User user = userRepository.findById(userLoginRequest.getId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        authenticateUser(user, request);
        redirectAttributes.addFlashAttribute("mensagem", "usuário cadastrado com sucesso!");
        return "redirect:/home";
    }

    private void authenticateUser(User user, HttpServletRequest request){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        System.out.println("Passed here");
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }
}

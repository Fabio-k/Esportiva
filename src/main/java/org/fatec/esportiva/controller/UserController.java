package org.fatec.esportiva.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.mapper.UserMapper;
import org.fatec.esportiva.model.Address;
import org.fatec.esportiva.model.User;
import org.fatec.esportiva.repository.UserRepository;
import org.fatec.esportiva.request.AddressRequest;
import org.fatec.esportiva.request.UserDto;
import org.fatec.esportiva.request.UserLoginRequest;
import org.fatec.esportiva.request.UserRequest;
import org.fatec.esportiva.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getUsers(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("user", new UserLoginRequest());
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        UserDto userDto = new UserDto();
        userDto.setAddress(new Address());
        model.addAttribute("user", new UserDto());
        model.addAttribute("body", "users/new.html :: content");
        return "layout";
    }

    @PostMapping("save")
    public String save(@ModelAttribute UserDto userDto){
        User savedUser = userService.save(UserMapper.toUser(userDto), userDto.getAddress());
        return "redirect:/dashboard";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginRequest userLoginRequest, HttpServletRequest request, RedirectAttributes redirectAttributes){
        User user = userRepository.findById(userLoginRequest.getId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        authenticateUser(user, request);
        redirectAttributes.addFlashAttribute("mensagem", "usuário cadastrado com sucesso!");
        return "redirect:/dashboard";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        userService.deleteUser(user);
        return "redirect:/dashboard";
    }

    private void authenticateUser(User user, HttpServletRequest request){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }
}

package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.mapper.AddressMapper;
import org.fatec.esportiva.mapper.UserMapper;
import org.fatec.esportiva.model.User;
import org.fatec.esportiva.request.UserRequest;
import org.fatec.esportiva.response.UserResponse;
import org.fatec.esportiva.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        User savedUser = userService.save(UserMapper.toUser(request), AddressMapper.toAddress(request.address()));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(savedUser));
    }
}

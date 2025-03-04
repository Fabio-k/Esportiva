package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.repository.AdminRepository;
import org.fatec.esportiva.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;

    public Optional<UserDetails> findByEmail(String email) {
        Optional<UserDetails> user = clientRepository.findUserByEmail(email);

        if (user.isEmpty()) {
            user = adminRepository.findUserByEmail(email);
        }

        return user;
    }

    public List<UserDetails> getUsers() {
        List<UserDetails> users = new ArrayList<>();
        users.addAll(adminRepository.findAll());
        users.addAll(clientRepository.findAll());

        return users;
    }
}

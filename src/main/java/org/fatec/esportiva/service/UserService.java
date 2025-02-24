package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.model.Address;
import org.fatec.esportiva.model.User;
import org.fatec.esportiva.model.enums.Role;
import org.fatec.esportiva.model.enums.Status;
import org.fatec.esportiva.repository.AddressRepository;
import org.fatec.esportiva.repository.UserRepository;
import org.fatec.esportiva.util.CodeGenerator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(User user, Address address){
        address.setId(null);
        Address userAddress = addressRepository.save(address);
        user.setAddress(userAddress);
        user.setStatus(Status.ACTIVE);
        user.setCode(generateUniqueCode());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getUsers(){
        List<User> users = userRepository.findAllByRole(Role.USER);

        return  users;
    }

    private String generateUniqueCode(){
        String code;
        int attempts = 0;
        do {
            code = CodeGenerator.generateCode(8);
            attempts++;
            if (attempts > 30){
                throw new RuntimeException("Falha ao tentar gerar código do usuário");
            }
        }while (userRepository.findByCode(code).isPresent());
        return code.toUpperCase();
    }

    public User getAuthenticatedUser() throws Exception{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof  User)){
            throw new Exception("Erro na autenticação");
        }
        User user = (User) principal;
        return  user;
    }
}

package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
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

    @Transactional
    public User save(User user, Address address){
        Address userAddress = addressRepository.save(address);
        user.setAddress(userAddress);
        user.setStatus(Status.ACTIVE);
        user.setCode(generateUniqueCode());
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User user){
        User actualUser = this.findUser(id);
        user.setId(id);
        user.setCode(actualUser.getCode());

        user.setRole(Role.USER);
        addressRepository.save(user.getAddress());
        return  userRepository.save(user);
    }

    public List<User> getUsers(){
        List<User> users = userRepository.findAllByRole(Role.USER);

        return  users;
    }

    public User findUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return user;
    }

    public void deleteUser(User user){
        userRepository.delete(user);
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

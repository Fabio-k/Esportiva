package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.model.Address;
import org.fatec.esportiva.model.User;
import org.fatec.esportiva.model.enums.Role;
import org.fatec.esportiva.model.enums.Status;
import org.fatec.esportiva.repository.AddressRepository;
import org.fatec.esportiva.repository.UserRepository;
import org.fatec.esportiva.util.CodeGenerator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public User save(User user, Address address){
        Address userAddress = addressRepository.save(address);
        user.setAddress(address);
        user.setStatus(Status.ACTIVE);
        user.setCode(CodeGenerator.generateCode(8));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }
}

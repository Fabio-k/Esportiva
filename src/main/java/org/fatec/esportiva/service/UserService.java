package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.mapper.AddressMapper;
import org.fatec.esportiva.model.Address;
import org.fatec.esportiva.model.User;
import org.fatec.esportiva.model.enums.Role;
import org.fatec.esportiva.model.enums.UserStatus;
import org.fatec.esportiva.repository.UserRepository;
import org.fatec.esportiva.request.UserDto;
import org.fatec.esportiva.util.CodeGenerator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressService addressService;

    @Transactional
    public User save(User user) {
        user.setStatus(UserStatus.ACTIVE);
        user.setCode(generateUniqueCode());
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, UserDto userDto) {
        User existingUser = this.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setRegistrationNumber(userDto.getRegistrationNumber());

        List<Address> updatedAddresses = userDto.getAddresses().stream().map(addressDto -> {
            if (addressDto.getId() != null) {
                Address address = addressService.findById(addressDto.getId())
                        .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
                updateAddressData(address, AddressMapper.toAddress(existingUser, addressDto));
                address.getAddressTypeList().clear();
                address.getAddressTypeList().addAll(addressDto.getTypes());
                return address;
            }
            return AddressMapper.toAddress(existingUser, addressDto);
        }).toList();

        existingUser.getAddresses().clear();
        existingUser.getAddresses().addAll(updatedAddresses);
        return userRepository.save(existingUser);
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAllByRole(Role.USER);

        return users;
    }

    public User findUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return user;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    private String generateUniqueCode() {
        String code;
        int attempts = 0;
        do {
            code = CodeGenerator.generateCode(8);
            attempts++;
            if (attempts > 30) {
                throw new RuntimeException("Falha ao tentar gerar código do usuário");
            }
        } while (userRepository.findByCode(code).isPresent());
        return code.toUpperCase();
    }

    private void updateAddressData(Address currentAddress, Address updatedAddress) {
        currentAddress.setCity(updatedAddress.getCity());
        currentAddress.setCep(updatedAddress.getCep());
        currentAddress.setNumber(updatedAddress.getNumber());
        currentAddress.setNeighborhood(updatedAddress.getNeighborhood());
        currentAddress.setState(updatedAddress.getState());
        currentAddress.setCountry(updatedAddress.getCountry());
        currentAddress.setObservation(updatedAddress.getObservation());
        currentAddress.setResidencyType(updatedAddress.getResidencyType());
        currentAddress.setStreetType(updatedAddress.getStreetType());
    }

    public User getAuthenticatedUser() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User)) {
            throw new Exception("Erro na autenticação");
        }
        User user = (User) principal;
        return user;
    }
}

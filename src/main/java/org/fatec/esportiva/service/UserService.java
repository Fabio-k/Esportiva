package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.mapper.AddressMapper;
import org.fatec.esportiva.entity.Address;
import org.fatec.esportiva.entity.Clients;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.fatec.esportiva.repository.UserRepository;
import org.fatec.esportiva.request.ClientDto;
import org.fatec.esportiva.util.CodeGenerator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AddressService addressService;

    @Transactional
    public Clients save(Clients user) {
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    @Transactional
    public Clients update(Long id, ClientDto userDto) {
        Clients existingUser = this.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setCpf(userDto.getCpf());
        existingUser.setGender(userDto.getGender());
        existingUser.setStatus(userDto.getStatus());

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

    public Clients findUser(Long id) {
        Clients user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return user;
    }

    public Optional<Clients> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Clients user) {
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
        currentAddress.setCep(updatedAddress.getCep());
        currentAddress.setNumber(updatedAddress.getNumber());
        currentAddress.setObservation(updatedAddress.getObservation());
        currentAddress.setResidencyType(updatedAddress.getResidencyType());
        currentAddress.setStreetType(updatedAddress.getStreetType());
    }

    public Clients getAuthenticatedUser() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Clients)) {
            throw new Exception("Erro na autenticação");
        }
        Clients user = (Clients) principal;
        return user;
    }
}

package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.*;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.fatec.esportiva.mapper.ClientMapper;
import org.fatec.esportiva.repository.ClientRepository;
import org.fatec.esportiva.request.ClientDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final AddressService addressService;
    private final ClientRepository clientRepository;
    private final CreditCardService creditCardService;
    private final AuthService authService;

    @Transactional
    public Client save(ClientDto clientDto) {
        Client client = ClientMapper.toUser(clientDto);
        client.setAddresses(addressService.createAddresses(client, clientDto.getAddresses()));
        client.setCreditCards(creditCardService.createCreditCards(client, clientDto.getCreditCards()));
        client.setStatus(UserStatus.ATIVO);
        return clientRepository.save(client);
    }

    @Transactional
    public Client update(Long id, ClientDto userDto) {
        Client existingUser = this.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setCpf(userDto.getCpf());
        existingUser.setGender(userDto.getGender());
        existingUser.setStatus(userDto.getStatus());
        existingUser.setDateBirth(userDto.getDateBirth());
        existingUser.setTelephoneType(userDto.getTelephoneType());
        existingUser.setTelephone(userDto.getTelephone());

        List<Address> updatedAddresses = addressService.updateOrCreateAddress(existingUser, userDto.getAddresses());
        List<CreditCard> updatedCreditCards = creditCardService.updateOrCreateCreditCards(existingUser,
                userDto.getCreditCards());

        existingUser.getAddresses().clear();
        existingUser.getAddresses().addAll(updatedAddresses);

        existingUser.getCreditCards().clear();
        existingUser.getCreditCards().addAll(updatedCreditCards);

        return clientRepository.save(existingUser);
    }

    public List<ClientDto> getClients(String name, String email, String cpf, UserStatus status, Gender gender) {
        String formattedName = normalize(name) == null ? null : "%" + name + "%";
        String formattedEmail = normalize(email) == null ? null : "%" + email + "%";
        List<ClientDto> clients = clientRepository
                .findWithFilter(formattedName, formattedEmail, normalize(cpf), status, gender).stream()
                .map(ClientMapper::toUserDto).toList();
        return clients;
    }

    public Client findClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return client;
    }

    public Client getAuthenticatedClient(){
        UserDetails userDetails = authService.getAuthenticatedUser();
        return clientRepository.findClientByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public void deleteClient(Client user) {
        clientRepository.delete(user);
    }

    private String normalize(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value;
    }

}

package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.AddressCategory;
import org.fatec.esportiva.entity.Cep;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.mapper.AddressMapper;
import org.fatec.esportiva.entity.Address;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.fatec.esportiva.mapper.ClientMapper;
import org.fatec.esportiva.repository.AddressCategoryRepository;
import org.fatec.esportiva.repository.AdminRepository;
import org.fatec.esportiva.repository.ClientRepository;
import org.fatec.esportiva.request.ClientDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final AddressService addressService;
    private final ClientRepository clientRepository;
    private final CepService cepService;
    private final AddressCategoryRepository addressCategoryRepository;

    @Transactional
    public Client save(ClientDto clientDto) {
        Client client = ClientMapper.toUser(clientDto);
        client.setAddresses(addressService.createAddresses(client, clientDto.getAddresses()));
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

        List<Address> updatedAddresses = userDto.getAddresses().stream().map(addressDto -> {
            Cep cep = cepService.findOrCreateByCep(addressDto);
            if (addressDto.getId() != null) {
                Address address = addressService.findById(addressDto.getId())
                        .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

                updateAddressData(address, AddressMapper.toAddress(existingUser, addressDto, cep));
                Set<AddressCategory> addressCategories = addressCategoryRepository
                        .findByAddressTypeIn(addressDto.getTypes());
                address.setAddressCategories(addressCategories);
                return address;
            }
            return AddressMapper.toAddress(existingUser, addressDto, cep);
        }).toList();

        existingUser.getAddresses().clear();
        existingUser.getAddresses().addAll(updatedAddresses);
        return clientRepository.save(existingUser);
    }

    public List<Client> getClients() {
        List<Client> clients = clientRepository.findAll();
        return clients;
    }

    public Client findClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return client;
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public void deleteClient(Client user) {
        clientRepository.delete(user);
    }

    private void updateAddressData(Address currentAddress, Address updatedAddress) {
        currentAddress.setCep(updatedAddress.getCep());
        currentAddress.setNumber(updatedAddress.getNumber());
        currentAddress.setObservation(updatedAddress.getObservation());
        currentAddress.setResidencyType(updatedAddress.getResidencyType());
        currentAddress.setStreetType(updatedAddress.getStreetType());
    }

}

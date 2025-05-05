package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.address.Address;
import org.fatec.esportiva.entity.address.AddressCategory;
import org.fatec.esportiva.entity.address.Cep;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.mapper.AddressMapper;
import org.fatec.esportiva.repository.AddressCategoryRepository;
import org.fatec.esportiva.repository.AddressRepository;
import org.fatec.esportiva.dto.request.AddressDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final CepService cepService;
    private final AddressCategoryRepository addressCategoryRepository;

    @Value("${creditCard.timeoutInMinutes}")
    private Long timeoutInMinutes;

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }

    public AddressDto createAddress(AddressDto addressDto, Boolean saveAddress, Client client){
        Address address = AddressMapper.toAddress(addressDto);
        Cep cep = cepService.findOrCreateByCep(addressDto);
        Set<AddressCategory> addressCategories = addressCategoryRepository
                .findByAddressTypeIn(addressDto.getTypes());
        address.setClient(client);
        address.setCep(cep);
        address.setAddressCategories(addressCategories);
        address.setTemporary(!saveAddress);
        if(!saveAddress) address.setExpiredAt(LocalDateTime.now().plusMinutes(timeoutInMinutes));
        return AddressMapper.toAddressDto(addressRepository.save(address));
    }

    public List<Address> createAddresses(Client client, List<AddressDto> addressDtoList) {
        List<Address> addresses = new ArrayList<>();
        addressDtoList.forEach(addressDto -> {
            Cep cep = cepService.findOrCreateByCep(addressDto);
            Set<AddressCategory> addressCategories = addressCategoryRepository
                    .findByAddressTypeIn(addressDto.getTypes());
            Address address = AddressMapper.toAddress(client, addressDto, cep);
            address.setAddressCategories(addressCategories);

            addresses.add(address);
        });

        return addresses;
    }

    public List<Address> updateOrCreateAddress(Client client, List<AddressDto> addressDtoList) {
        return addressDtoList.stream().map(addressDto -> {
            Cep cep = cepService.findOrCreateByCep(addressDto);
            Set<AddressCategory> addressCategories = addressCategoryRepository
                    .findByAddressTypeIn(addressDto.getTypes());
            if (addressDto.getId() != null) {
                Address address = findById(addressDto.getId());

                address.setAddressIdentificationPhrase(addressDto.getAddressIdentificationPhrase());
                address.setCep(cep);
                address.setNumber(addressDto.getNumber());
                address.setObservation(addressDto.getObservation());
                address.setResidencyType(addressDto.getResidencyType());
                address.setStreetType(addressDto.getStreetType());

                address.setAddressCategories(addressCategories);
                return address;
            }

            Address address = AddressMapper.toAddress(client, addressDto, cep);
            address.setAddressCategories(addressCategories);
            return address;
        }).toList();
    }
}

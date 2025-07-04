package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.address.Address;
import org.fatec.esportiva.entity.address.Cep;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.dto.request.AddressDto;
import org.fatec.esportiva.dto.response.AddressResponseDto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class AddressMapper {
    public Address toAddress(AddressDto addressDto){
        return Address.builder()
                .addressIdentificationPhrase(addressDto.getAddressIdentificationPhrase())
                .number(addressDto.getNumber())
                .streetType(addressDto.getStreetType())
                .residencyType(addressDto.getResidencyType())
                .observation(addressDto.getObservation())
                .build();
    }

    public Address toAddress(Client client, AddressDto addressDto, Cep cep) {
        return Address.builder()
                .id(addressDto.getId())
                .client(client)
                .cep(cep)
                .addressIdentificationPhrase(addressDto.getAddressIdentificationPhrase())
                .number(addressDto.getNumber())
                .streetType(addressDto.getStreetType())
                .residencyType(addressDto.getResidencyType())
                .observation(addressDto.getObservation())
                .build();
    }

    public AddressDto toAddressDto(Address address) {
        Cep cep = address.getCep();
        return AddressDto.builder()
                .id(address.getId())
                .addressIdentificationPhrase(address.getAddressIdentificationPhrase())
                .number(address.getNumber())
                .cep(cep.getCep())
                .street(cep.getStreet())
                .neighborhood(cep.getNeighborhood())
                .city(cep.getCity())
                .state(cep.getState())
                .country(cep.getCountry())
                .streetType(address.getStreetType())
                .residencyType(address.getResidencyType())
                .observation(address.getObservation())
                .types(new HashSet<>(address.getAddressCategories()
                        .stream()
                        .map(cat -> cat.getAddressType())
                        .collect(Collectors.toSet())))
                .build();
    }

    public List<AddressDto> toAddressDtoList(List<Address> addresses) {
        return addresses.stream().map(address -> AddressMapper.toAddressDto(address)).toList();
    }

    public AddressResponseDto toAddressDtoResponse(Address address, BigDecimal freight, String formattedFreight){
        return AddressResponseDto.builder()
                .id(address.getId())
                .addressIdentificationPhrase(address.getAddressIdentificationPhrase())
                .number(address.getNumber())
                .street(address.getCep().getStreet())
                .city(address.getCep().getCity())
                .state(address.getCep().getState())
                .cep(address.getCep().getCep())
                .freight(freight)
                .formattedFreight(formattedFreight)
                .build();
    }
}

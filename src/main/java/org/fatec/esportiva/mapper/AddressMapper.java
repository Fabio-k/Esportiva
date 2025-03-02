package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Address;
import org.fatec.esportiva.entity.User;
import org.fatec.esportiva.request.AddressDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@UtilityClass
public class AddressMapper {

    public Address toAddress(User user, AddressDto addressDto){
        return Address.builder()
                .user(user)
                .id(addressDto.getId())
                .cep(addressDto.getCep())
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .state(addressDto.getState())
                .neighborhood(addressDto.getNeighborhood())
                .number(addressDto.getNumber())
                .streetType(addressDto.getStreetType())
                .residencyType(addressDto.getResidencyType())
                .observation(addressDto.getObservation())
                .addressTypeList(new ArrayList<>(addressDto.getTypes()))
                .build();
    }

    public List<Address> toAddressList(User user, List<AddressDto> addressDtoList){
        return addressDtoList.stream().map(adressDto -> AddressMapper.toAddress(user, adressDto)).toList();
    }

    public AddressDto toAddressDto(Address address){
        return AddressDto.builder()
                .id(address.getId())
                .cep(address.getCep())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .state(address.getState())
                .neighborhood(address.getNeighborhood())
                .number(address.getNumber())
                .streetType(address.getStreetType())
                .residencyType(address.getResidencyType())
                .observation(address.getObservation())
                .types(new HashSet<>(address.getAddressTypeList()))
                .build();
    }

    public  List<AddressDto> toAddressDtoList(List<Address> addresses){
        return addresses.stream().map(address -> AddressMapper.toAddressDto(address)).toList();
    }
}

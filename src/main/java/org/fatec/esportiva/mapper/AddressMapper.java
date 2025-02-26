package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.model.Address;
import org.fatec.esportiva.request.AddressDto;

@UtilityClass
public class AddressMapper {

    public Address toAddress(AddressDto addressDto){
        return Address.builder()
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
                .build();
    }
}

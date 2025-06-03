package org.fatec.esportiva.utils;

import org.fatec.esportiva.entity.address.Address;
import org.fatec.esportiva.entity.address.ResidencyType;
import org.fatec.esportiva.entity.address.StreetType;

public class AddressFactory {
    public static Address defaultAddress(){
        return Address.builder()
                .cep(CepFactory.defaultCep())
                .number("123")
                .addressIdentificationPhrase("Casa")
                .observation("perto da praça X")
                .streetType(StreetType.RUA)
                .residencyType(ResidencyType.CASA)
                .temporary(false)
                .build();
    }
}

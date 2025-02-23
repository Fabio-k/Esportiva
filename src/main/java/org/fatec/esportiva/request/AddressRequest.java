package org.fatec.esportiva.request;

import org.fatec.esportiva.model.enums.ResidencyType;
import org.fatec.esportiva.model.enums.StreetType;

public record AddressRequest(String cep, ResidencyType residencyType, StreetType streetType, String number, String neighborhood, String city, String state, String country, String observation) {
}

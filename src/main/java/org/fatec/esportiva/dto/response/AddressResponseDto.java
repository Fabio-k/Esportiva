package org.fatec.esportiva.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AddressResponseDto {
    private Long id;
    private String addressIdentificationPhrase;
    private String cep;
    private String street;
    private String city;
    private String state;
    private BigDecimal freight;
    private String formattedFreight;
}

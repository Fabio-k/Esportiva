package org.fatec.esportiva.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

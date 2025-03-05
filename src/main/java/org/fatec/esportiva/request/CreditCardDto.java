package org.fatec.esportiva.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import org.fatec.esportiva.entity.enums.CreditCardBrand;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class CreditCardDto {
    private Long id;

    @Pattern(regexp = "^[0-9]{13,19}$", message = "O campo deve conter um número de cartão de crédito válido")
    @NotBlank
    private String number;

    @NotNull
    private CreditCardBrand brand;

    @NotBlank
    private String name;

    @Pattern(regexp = "^[0-9]+$", message = "O código de segurança somente contém dígitos")
    @NotBlank
    private String securityCode;

    private boolean preferential;
}

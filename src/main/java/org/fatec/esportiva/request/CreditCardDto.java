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

    @NotBlank(message = "Cartão de crédito: Número não pode ficar em branco")
    @Pattern(regexp = "^[0-9]{13,19}$", message = "Cartão de crédito: O campo deve conter um número de cartão de crédito válido")
    private String number;

    @NotNull(message = "Cartão de crédito: Bandeira não pode ficar em branco")
    private CreditCardBrand brand;

    @NotBlank(message = "Cartão de crédito: Nome do cliente não pode ficar em branco")
    private String name;

    @NotBlank(message = "Cartão de crédito: Código de segurança não pode ficar em branco")
    @Pattern(regexp = "^[0-9]+$", message = "Cartão de crédito: O código de segurança somente contém dígitos")
    private String securityCode;

    @NotNull(message = "Cartão de crédito: Flag de cartão preferencial não pode ficar em branco")
    private boolean preferential;
}

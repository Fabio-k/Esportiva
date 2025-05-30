package org.fatec.esportiva.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import org.fatec.esportiva.entity.enums.CreditCardBrand;
import org.fatec.esportiva.utils.StringUtils;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class CreditCardDto {
    private Long id;

    @NotBlank(message = "Número não pode ficar em branco")
    @Pattern(regexp = "^[0-9]{13,19}$", message = "O campo deve conter um número de cartão de crédito válido (13 a 19 dígitos)")
    private String number;

    @NotNull(message = "Bandeira não pode ficar em branco")
    private CreditCardBrand brand;

    @NotBlank(message = "Nome do cliente não pode ficar em branco")
    private String name;

    @NotBlank(message = "Código de segurança não pode ficar em branco")
    @Pattern(regexp = "^[0-9]+$", message = "O código de segurança somente contém dígitos")
    private String securityCode;

    private boolean preferential;

    public void setNumber(String number) {
        if (number != null) {
            this.number = number.replaceAll("\\D", "");
        }
    }

    public String numberMask(){
       return StringUtils.maskCreditCardNumber(this.number);
    }
}

package org.fatec.esportiva.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SplitCreditCardDto {
    private Long id;
    private String number;
    private BigDecimal value;
}

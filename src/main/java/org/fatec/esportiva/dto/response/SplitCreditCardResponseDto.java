package org.fatec.esportiva.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SplitCreditCardResponseDto {
    private String number;
    private String formattedPrice;
}

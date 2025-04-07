package org.fatec.esportiva.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ExchangeVoucherResponseDto {

    private Long id;

    private BigDecimal value;

    private int quantity;

    private String formattedValue;
}

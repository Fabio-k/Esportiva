package org.fatec.esportiva.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class ExchangeVoucherResponseDto {

    private Long id;

    private BigDecimal value;

    private int quantity;

    private String formattedValue;
}

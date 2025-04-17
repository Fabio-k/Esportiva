package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.response.CartItemResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreightService {
    private static final BigDecimal BASE_FREIGHT = BigDecimal.TEN;
    private static final BigDecimal SP_MULTIPLIER = BigDecimal.TWO;
    private static final String SAO_PAULO = "São Paulo";
    private static final BigDecimal OTHER_MULTIPLIER = BigDecimal.valueOf(4);

    public BigDecimal calculate(String state, List<CartItemResponseDto> cartItemList){
        int itemQuantity = cartItemList.stream().mapToInt(CartItemResponseDto::quantity).sum();
        BigDecimal quantityPrice = BigDecimal.valueOf(itemQuantity);
        if(SAO_PAULO.equalsIgnoreCase(state)){
            quantityPrice = quantityPrice.multiply(SP_MULTIPLIER);
            return BASE_FREIGHT.add(quantityPrice);
        }
        quantityPrice = quantityPrice.multiply(OTHER_MULTIPLIER);
        return BASE_FREIGHT.add(quantityPrice);
    }
}

package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.CartItem;
import org.fatec.esportiva.response.CartItemResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreightService {

    public BigDecimal calculate(String state, List<CartItemResponseDto> cartItemList){
        int itemQuantity = cartItemList.stream().mapToInt(CartItemResponseDto::quantity).sum();
        BigDecimal baseValue = BigDecimal.TEN;
        BigDecimal quantityPrice = BigDecimal.valueOf(itemQuantity);
        if(state.equals("São Paulo")){
            quantityPrice = quantityPrice.multiply(BigDecimal.TWO);
            return baseValue.add(quantityPrice);
        }
        quantityPrice = quantityPrice.multiply(BigDecimal.valueOf(4));
        return baseValue.add(quantityPrice);
    }
}

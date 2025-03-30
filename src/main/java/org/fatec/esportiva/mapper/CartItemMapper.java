package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.CartItem;
import org.fatec.esportiva.response.CartItemResponseDto;

@UtilityClass
public class CartItemMapper {
    public CartItemResponseDto toCartItemResponseDto(CartItem cartItem){
        return new CartItemResponseDto(cartItem.getId(), cartItem.getQuantity(), ProductMapper.toProductResponseDto(cartItem.getProduct()));
    }
}

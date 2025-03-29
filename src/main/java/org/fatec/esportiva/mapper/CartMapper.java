package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Cart;
import org.fatec.esportiva.response.CartResponseDto;

@UtilityClass
public class CartMapper {
    public CartResponseDto toCartResponseDto(Cart cart){
        return new CartResponseDto(cart.getCartItems().stream()
                .map(CartItemMapper::toCartItemResponseDto)
                .toList()
        );
    }
}

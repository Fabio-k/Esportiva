package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.CartItem;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.dto.response.CartItemResponseDto;

@UtilityClass
public class CartItemMapper {
    public CartItemResponseDto toCartItemResponseDto(CartItem cartItem){
        return new CartItemResponseDto(cartItem.getId(), cartItem.getQuantity(), ProductMapper.toProductResponseDto(cartItem.getProduct()));
    }

    public Order toOrder(CartItem cartItem){
        return Order.builder()
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .build();
    }
}

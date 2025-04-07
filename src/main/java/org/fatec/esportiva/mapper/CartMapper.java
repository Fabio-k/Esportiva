package org.fatec.esportiva.mapper;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Cart;
import org.fatec.esportiva.response.CartResponseDto;
import org.fatec.esportiva.service.CurrencyService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final CurrencyService currencyService;

    public CartResponseDto toCartResponseDto(Cart cart){
        return new CartResponseDto(cart.getCartItems().stream()
                .map(CartItemMapper::toCartItemResponseDto)
                .toList(), currencyService
        );
    }
}

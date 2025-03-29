package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Cart;
import org.fatec.esportiva.entity.CartItem;
import org.fatec.esportiva.mapper.CartMapper;
import org.fatec.esportiva.repository.CartItemRepository;
import org.fatec.esportiva.repository.CartRepository;
import org.fatec.esportiva.request.CartItemRequestDto;
import org.fatec.esportiva.response.CartResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final ClientService clientService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public CartResponseDto addItem(CartItemRequestDto dto) throws Exception{
        Cart cart = clientService.getAuthenticatedClient().getCart();
        CartItem cartItem = productService.updateQuantity(dto.id(), dto.quantity());

        cartItem.setCart(cart);
        cartItem = cartItemRepository.save(cartItem);

        cart.getCartItems().add(cartItem);

        return CartMapper.toCartResponseDto(cart);
    }
}

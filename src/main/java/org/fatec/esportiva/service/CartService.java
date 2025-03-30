package org.fatec.esportiva.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Cart;
import org.fatec.esportiva.entity.CartItem;
import org.fatec.esportiva.mapper.CartItemMapper;
import org.fatec.esportiva.mapper.CartMapper;
import org.fatec.esportiva.repository.CartItemRepository;
import org.fatec.esportiva.repository.CartRepository;
import org.fatec.esportiva.request.CartItemRequestDto;
import org.fatec.esportiva.response.CartItemResponseDto;
import org.fatec.esportiva.response.CartResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final ClientService clientService;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Transactional
    public CartItemResponseDto addItem(CartItemRequestDto dto){
        Cart cart = clientService.getAuthenticatedClient().getCart();
        CartItem cartItem = productService.updateQuantity(dto.id(), dto.quantity());
        cartItem.setCart(cart);

        Optional<CartItem> optionalItem = getCartItemByProductId(cart.getCartItems(), cartItem.getProduct().getId());
        if (optionalItem.isPresent()){
            CartItem existingItem = optionalItem.get();
            existingItem.setQuantity((short) (cartItem.getQuantity() + existingItem.getQuantity()));
            cartItem = existingItem;
        }
        cartItem = cartItemRepository.save(cartItem);
        updateCartCreatedAt();
        return CartItemMapper.toCartItemResponseDto(cartItem);
    }

    public CartResponseDto getCart() throws Exception{
        Cart cart = clientService.getAuthenticatedClient().getCart();

        return CartMapper.toCartResponseDto(cart);
    }

    @Transactional
    public void removeItem(Long id, Short quantity, Cart cart){
        CartItem cartItem = findCartItemById(id);
        if(quantity == null || cartItem.getQuantity() < quantity){
            quantity = cartItem.getQuantity();
        }
        cartItem.setQuantity((short) (cartItem.getQuantity() - quantity));
        productService.returnBlockedProductQuantity(cartItem.getProduct().getId(), quantity);
        updateCartCreatedAt(cart);
        if (cartItem.getQuantity() == 0){
            cartItemRepository.delete(cartItem);
            return;
        }
        cartItemRepository.save(cartItem);
    }

    private void updateCartCreatedAt(Cart cart){
        if(cart == null){
            cart = clientService.getAuthenticatedClient().getCart();
        }
        cart.setCreatedAt(LocalDateTime.now().plusMinutes(5));
        cartRepository.save(cart);
    }

    private void updateCartCreatedAt(){
        Cart cart = clientService.getAuthenticatedClient().getCart();
        cart.setCreatedAt(LocalDateTime.now().plusMinutes(5));
        System.out.println(cart.getCreatedAt());
        cartRepository.save(cart);
    }

    private CartItem findCartItemById(Long id){
        return cartItemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado"));
    }

    private Optional<CartItem> getCartItemByProductId(List<CartItem> cartItems, Long productId){
        return  cartItems.stream().filter(item ->
                item.getProduct().getId().equals(productId)).findFirst();
    }


}

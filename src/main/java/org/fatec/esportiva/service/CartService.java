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
import org.fatec.esportiva.dto.request.CartItemRequestDto;
import org.fatec.esportiva.dto.response.CartItemResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final CartMapper cartMapper;

    @Value("${cart.product.timeoutInMinutes}")
    private int productTimeoutInMinutes;

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

    public void cleanCart(){
        Cart cart = clientService.getAuthenticatedClient().getCart();
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    public BigDecimal getTotalCartPrice(){
        Cart cart = clientService.getAuthenticatedClient().getCart();
        BigDecimal total = cart.getCartItems().stream()
                .map(this::getItemTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total;
    }

    public void setToNotified(Long id, Boolean isNotified){
        Cart cart = findCartById(id);
        cart.setIsNotified(isNotified);
        cartRepository.save(cart);
    }

    private BigDecimal getItemTotalPrice(CartItem cartItem){
        BigDecimal quantity = BigDecimal.valueOf(cartItem.getQuantity());
        return cartItem.getProduct().getPriceWithMargin().multiply(quantity);
    }

    private void updateCartCreatedAt(Cart cart){
        if(cart == null){
            cart = clientService.getAuthenticatedClient().getCart();
        }
        cart.setCreatedAt(LocalDateTime.now().plusMinutes(productTimeoutInMinutes));
        cartRepository.save(cart);
    }

    private void updateCartCreatedAt(){
        Cart cart = clientService.getAuthenticatedClient().getCart();
        cart.setCreatedAt(LocalDateTime.now().plusMinutes(productTimeoutInMinutes));
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

    private Cart findCartById(Long id){
        return cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado"));
    }


}

package org.fatec.esportiva.service.cart;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.cart.Cart;
import org.fatec.esportiva.entity.cart.CartItem;
import org.fatec.esportiva.mapper.CartItemMapper;
import org.fatec.esportiva.repository.CartItemRepository;
import org.fatec.esportiva.repository.CartRepository;
import org.fatec.esportiva.dto.request.CartItemRequestDto;
import org.fatec.esportiva.dto.response.CartItemResponseDto;
import org.fatec.esportiva.service.ClientService;
import org.fatec.esportiva.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final ClientService clientService;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CartItemManager cartItemManager;

    @Value("${cart.product.timeoutInMinutes}")
    private int productTimeoutInMinutes;

    @Transactional
    public CartItemResponseDto addItem(CartItemRequestDto dto) {
        Cart cart = clientService.getAuthenticatedClient().getCart();

        CartItem cartItem = cartItemManager.createAndValidateItem(cart, dto, productService);

        cartItem = cartItemRepository.save(cartItem);

        updateCartCreatedAt();

        cart.getRemovedProducts().remove(cartItem.getProduct());

        return CartItemMapper.toCartItemResponseDto(cartItem);
    }

    @Transactional
    public void removeItem(Long id, Short quantity, Cart cart) {
        CartItem cartItem = findCartItemById(id);
        if (quantity == null || cartItem.getQuantity() < quantity) {
            quantity = cartItem.getQuantity();
        }
        cartItem.setQuantity((short) (cartItem.getQuantity() - quantity));
        productService.returnBlockedProductQuantity(cartItem.getProduct().getId(), quantity);
        updateCartCreatedAt(cart);
        if (cartItem.getQuantity() == 0) {
            cartItemRepository.delete(cartItem);
            return;
        }
        cartItemRepository.save(cartItem);
    }

    public void cleanCart() {
        Cart cart = clientService.getAuthenticatedClient().getCart();
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    public BigDecimal getTotalCartPrice() {
        Cart cart = clientService.getAuthenticatedClient().getCart();
        return  cart.getTotalPrice();
    }

    public void setToNotified(Long id, Boolean isNotified) {
        Cart cart = findCartById(id);
        cart.setIsNotified(isNotified);
        cartRepository.save(cart);
    }

    public void updateCartCreatedAt(Cart cart) {
        if (cart == null) {
            cart = clientService.getAuthenticatedClient().getCart();
        }
        cart.setCreatedAt(LocalDateTime.now().plusMinutes(productTimeoutInMinutes));
        cartRepository.save(cart);
    }

    private void updateCartCreatedAt() {
        Cart cart = clientService.getAuthenticatedClient().getCart();
        cart.setCreatedAt(LocalDateTime.now().plusMinutes(productTimeoutInMinutes));
        cartRepository.save(cart);
    }

    public void removeExpiredItem(Cart cart) {
        Optional<CartItem> cartItem = cartItemRepository.findTopByCartIdOrderByInclusionTimeAsc(cart.getId());
        cartItem.ifPresent(item -> {
            cart.getRemovedProducts().add(item.getProduct());
            cartRepository.save(cart);
            cartItemRepository.delete(item);
            productService.returnBlockedProductQuantity(item.getProduct().getId(), item.getQuantity());
        });
    }

    private CartItem findCartItemById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado"));
    }

    private Cart findCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carrinho não encontrado"));
    }
}

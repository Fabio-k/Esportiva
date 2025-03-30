package org.fatec.esportiva.sheduledTasks;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Cart;
import org.fatec.esportiva.entity.CartItem;
import org.fatec.esportiva.repository.CartRepository;
import org.fatec.esportiva.service.CartService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartScheduledTask {
    private final CartRepository cartRepository;
    private final CartService cartService;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void freeCarts(){
        List<Cart> carts =  cartRepository.findByCreatedAtBefore(LocalDateTime.now());
        carts.forEach(cart -> {
            removeItems(cart);
            cart.setCreatedAt(LocalDateTime.now().plusMinutes(5));
            cartRepository.save(cart);
        });
    }

    private void removeItems(Cart cart){
        List<CartItem> copyCartItems = new ArrayList<>(cart.getCartItems());
        copyCartItems.forEach(cartItem -> {
            System.out.println("Removing: " + cartItem.getProduct().getName() + LocalDateTime.now());
            cartService.removeItem(cartItem.getId(), null, cart);
        });
    }
}

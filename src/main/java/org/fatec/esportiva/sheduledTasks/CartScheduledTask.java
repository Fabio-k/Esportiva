package org.fatec.esportiva.sheduledTasks;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Cart;
import org.fatec.esportiva.entity.CartItem;
import org.fatec.esportiva.repository.CartRepository;
import org.fatec.esportiva.service.CartService;
import org.fatec.esportiva.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
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
    private final NotificationService notificationService;
    private final static String cartTimeoutMessage = "Os itens do carrinho serão removidos em 5 minutos";

    @Value("${cart.product.timeoutInMinutes}")
    private int cartTimeoutInMinutes;

    @Value("${cart.expire.notification.inMinutes}")
    private int cartExpireNotificationInMinutes;

    @Scheduled(fixedRateString = "${cart.cleanup.intervalInMillis:60000}")
    @Transactional
    public void freeCarts(){
        List<Cart> carts =  cartRepository.findByCreatedAtBefore(LocalDateTime.now());
        carts.forEach(cart -> {
            removeItems(cart);
            cart.setCreatedAt(LocalDateTime.now().plusMinutes(cartTimeoutInMinutes));
            cartRepository.save(cart);
        });
    }

    @Scheduled(fixedRateString = "${cart.expire.notification.intervalInMillis:60000}")
    @Transactional
    public void notifyClientsAboutCartTimeout(){
        List<Cart> carts =  cartRepository.findByCreatedAtBeforeAndIsNotifiedFalse(LocalDateTime.now().plusMinutes(cartExpireNotificationInMinutes));
        carts.forEach(cart -> {
            List<CartItem> cartItems = cart.getCartItems();
            if(!cartItems.isEmpty()){
                notificationService.createNotification(cart.getClient(), cartTimeoutMessage);
                cartService.setToNotified(cart.getId(), true);
            }
        });
    }

    private void removeItems(Cart cart){
        List<CartItem> copyCartItems = new ArrayList<>(cart.getCartItems());
        copyCartItems.forEach(cartItem -> {
            cartService.removeItem(cartItem.getId(), null, cart);
            cartService.setToNotified(cart.getId(), false);
        });
    }
}

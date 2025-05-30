package org.fatec.esportiva.validation;

import org.fatec.esportiva.entity.cart.Cart;
import org.fatec.esportiva.entity.cart.CartItem;
import org.fatec.esportiva.exception.EmptyCartException;
import org.fatec.esportiva.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartEmptyValidator {
    @Autowired
    private ClientService clientService;
    public void validate(){
        Cart cart = clientService.getAuthenticatedClient().getCart();
        List<CartItem> cartItemList = cart.getCartItems();

        if (!cartItemList.isEmpty() && cart.getRemovedProducts().isEmpty()) return;
        throw new EmptyCartException();
    }
}

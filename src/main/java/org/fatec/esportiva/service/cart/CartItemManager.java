package org.fatec.esportiva.service.cart;

import org.fatec.esportiva.dto.request.CartItemRequestDto;
import org.fatec.esportiva.entity.cart.Cart;
import org.fatec.esportiva.entity.cart.CartItem;
import org.fatec.esportiva.entity.product.Product;
import org.fatec.esportiva.exception.ApiException;
import org.fatec.esportiva.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CartItemManager {
    public CartItem createAndValidateItem(Cart cart, CartItemRequestDto dto, ProductService productService){
        if(dto.quantity() < 0) throw new ApiException("Quantidade não pode ser menor que zero");

        Product product = productService.updateQuantity(dto.id(), dto.quantity());
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(dto.quantity());
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        Optional<CartItem> optionalItem = getCartItemByProductId(cart.getCartItems(), cartItem.getProduct().getId());
        if (optionalItem.isPresent()) {
            CartItem existingItem = optionalItem.get();
            existingItem.setQuantity((short) (cartItem.getQuantity() + existingItem.getQuantity()));
            cartItem = existingItem;
        }

        return cartItem;
    }

    private Optional<CartItem> getCartItemByProductId(List<CartItem> cartItems, Long productId) {
        return cartItems.stream().filter(item ->
                item.getProduct().getId().equals(productId)).findFirst();
    }
}

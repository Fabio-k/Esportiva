package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.CartItem;
import org.fatec.esportiva.request.CartItemRequestDto;
import org.fatec.esportiva.response.CartItemResponseDto;
import org.fatec.esportiva.response.CartResponseDto;
import org.fatec.esportiva.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartApiController {
    private final CartService cartService;
    @PostMapping("/product")
    public ResponseEntity<CartItemResponseDto> addProduct(@RequestBody CartItemRequestDto dto) throws Exception {
        CartItemResponseDto cartItem = cartService.addItem(dto);
        return ResponseEntity.ok(cartItem);
    }
}

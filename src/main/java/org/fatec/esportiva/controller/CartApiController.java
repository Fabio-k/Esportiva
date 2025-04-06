package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.request.CartItemRequestDto;
import org.fatec.esportiva.response.CartItemResponseDto;
import org.fatec.esportiva.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long id, @RequestParam(name = "quantity", required = false) Short quantity) throws Exception{
        cartService.removeItem(id, quantity, null);
        return ResponseEntity.ok().build();
    }
}

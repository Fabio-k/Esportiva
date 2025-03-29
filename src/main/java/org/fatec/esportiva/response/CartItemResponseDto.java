package org.fatec.esportiva.response;

import org.fatec.esportiva.entity.Product;

public record CartItemResponseDto(Long id, Short quantity, ProductResponseDto product) {
}

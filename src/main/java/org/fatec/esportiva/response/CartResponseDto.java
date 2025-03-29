package org.fatec.esportiva.response;

import java.util.List;

public record CartResponseDto(List<CartItemResponseDto> items) {}

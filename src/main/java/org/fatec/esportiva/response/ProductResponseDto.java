package org.fatec.esportiva.response;

import java.math.BigDecimal;

public record ProductResponseDto(Long id, int quantity, String name, BigDecimal price, String description, String image) {}

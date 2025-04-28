package org.fatec.esportiva.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductCategoryResponseDto {
    private Long id;
    private String name;
}

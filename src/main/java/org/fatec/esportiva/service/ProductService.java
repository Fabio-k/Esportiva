package org.fatec.esportiva.service;

import java.util.List;

import org.fatec.esportiva.mapper.ProductMapper;
import org.fatec.esportiva.repository.ProductRepository;
import org.fatec.esportiva.request.ProductDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> getProducts(String name) {
        List<ProductDto> clients = productRepository
                .findWithFilter(name).stream()
                .map(ProductMapper::toProductDto).toList();
        return clients;
    }

}

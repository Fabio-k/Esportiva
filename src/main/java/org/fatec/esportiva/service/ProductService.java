package org.fatec.esportiva.service;

import java.util.List;
import java.util.Optional;

import org.fatec.esportiva.entity.Product;
import org.fatec.esportiva.entity.enums.ProductStatus;
import org.fatec.esportiva.mapper.ProductMapper;
import org.fatec.esportiva.repository.ProductRepository;
import org.fatec.esportiva.request.ProductDto;
import org.fatec.esportiva.response.ProductResponseDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> getProducts(String name) {
        List<ProductDto> products = productRepository
                .findWithFilter(name).stream()
                .map(ProductMapper::toProductDto).toList();
        return products;
    }

    public List<ProductResponseDto> getAllProducts(){
        return productRepository.findAll().stream()
                .map(ProductMapper::productResponseDto).toList();
    }

    public Product findProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return product;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

}

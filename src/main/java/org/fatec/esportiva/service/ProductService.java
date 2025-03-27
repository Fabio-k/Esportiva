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
                .findWithFilter(name, null, null).stream()
                .map(ProductMapper::toProductDto).toList();
        return products;
    }

    public List<ProductResponseDto> getProductsSummary(){
        return  productRepository.findAllByStatus(ProductStatus.ATIVO)
                .stream().map(ProductMapper::productResponseDto).toList();
    }

    public List<ProductResponseDto> findProductsSummary(String name, Integer maxValue){
        return productRepository
                .findWithFilter(name, ProductStatus.ATIVO, maxValue).stream()
                .map(ProductMapper::productResponseDto).toList();
    }


    public ProductResponseDto findProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return ProductMapper.productResponseDto(product);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

}

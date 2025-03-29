package org.fatec.esportiva.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.fatec.esportiva.entity.CartItem;
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
                .findWithFilter(name, null, null, null).stream()
                .map(ProductMapper::toProductDto).toList();
        return products;
    }

    public List<ProductResponseDto> getProductsSummary(){
        return  productRepository.findAllByStatus(ProductStatus.ATIVO)
                .stream().map(ProductMapper::toProductResponseDto).toList();
    }

    public List<ProductResponseDto> findProductsSummary(String name, Integer maxValue, String category){
        return productRepository
                .findWithFilter(name, ProductStatus.ATIVO, maxValue, category).stream()
                .map(ProductMapper::toProductResponseDto).toList();
    }


    public ProductResponseDto findProduct(Long id) {
        return ProductMapper.toProductResponseDto(findById(id));
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public CartItem updateQuantity(Long id, Short quantity) {
        Product product = findById(id);
        Integer availableStock = product.getStockQuantity() - product.getBlockedQuantity();
        if(availableStock < quantity){
            throw new IllegalArgumentException("Estoque insuficiente");
        }
        product.setBlockedQuantity(product.getBlockedQuantity() + quantity);
        Product savedProduct = productRepository.save(product);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setProduct(savedProduct);
        return cartItem;
    }

    public void returnBlockedProductQuantity(Long id, Short quantity){
        Product product = findById(id);
        if(product.getBlockedQuantity() < quantity) throw new IllegalArgumentException("Erro no estoque");
        product.setBlockedQuantity(product.getBlockedQuantity() - quantity);
        productRepository.save(product);
    }
}

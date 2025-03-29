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

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product save(ProductDto productDto) {
        Product product = ProductMapper.toProduct(productDto);
        product.setStatus(ProductStatus.ATIVO);
        return productRepository.save(product);
    }

    // costValue é tratado como int porque ele é usado como filtro. Para o usuário,
    // basta usar valores inteiros
    public List<ProductDto> getProducts(String name, ProductStatus inactivationCategory, int costValue,
            String category) {
        List<ProductDto> products = productRepository
                .findWithFilter(name, inactivationCategory, costValue, category).stream()
                .map(ProductMapper::toProductDto).toList();
        return products;
    }

    public List<ProductResponseDto> getProductsSummary() {
        return productRepository.findAllByStatus(ProductStatus.ATIVO)
                .stream().map(ProductMapper::productResponseDto).toList();
    }

    public List<ProductResponseDto> findProductsSummary(String name, Integer maxValue, String category) {
        return productRepository
                .findWithFilter(name, ProductStatus.ATIVO, maxValue, category).stream()
                .map(ProductMapper::productResponseDto).toList();
    }

    public Optional<Product> findProduct2(Long id) {
        return productRepository.findById(id);
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

    public void deleteClient(Optional<Product> product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteClient'");
    }

}

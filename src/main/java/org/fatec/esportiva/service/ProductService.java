package org.fatec.esportiva.service;

import java.time.LocalDate;
import java.util.*;

import jakarta.persistence.EntityNotFoundException;
import org.fatec.esportiva.dto.request.ProductCategoryDto;
import org.fatec.esportiva.dto.response.AdminProductResponseDto;
import org.fatec.esportiva.dto.response.ProductCategoryResponseDto;
import org.fatec.esportiva.entity.product.Product;
import org.fatec.esportiva.entity.product.ProductCategory;
import org.fatec.esportiva.entity.product.ProductStatus;
import org.fatec.esportiva.exception.ApiException;
import org.fatec.esportiva.mapper.ProductMapper;
import org.fatec.esportiva.repository.ProductCategoryRepository;
import org.fatec.esportiva.repository.ProductRepository;
import org.fatec.esportiva.dto.request.ProductDto;
import org.fatec.esportiva.dto.response.ProductResponseDto;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ClientService clientService;

    public List<ProductCategoryDto> getAllProducts(){
        return productRepository.findAll().stream()
                .map(product -> new ProductCategoryDto(product.getId(), product.getName())).toList();
    }

    @Transactional
    public Product save(ProductDto productDto) {
        Product product = ProductMapper.toProduct(productDto);
        List<ProductCategory> categories = productCategoryRepository.findAllByIdIn(productDto.getProductCategoryIds());

        product.setCategories(categories);
        product.setEntryDate(LocalDate.now());
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

    public List<ProductResponseDto> getAllAvailableProducts(){
        return productRepository.findAvailableProducts(ProductStatus.ATIVO).stream()
                .map(ProductMapper::toProductResponseDto)
                .toList();
    }

    public List<ProductResponseDto> getRecommendedProducts(){
        if(!clientService.isClientAuthenticated()) return List.of();
        List<Product> products = clientService.getClientProducts();
        Set<Long> categoriesIds = new HashSet<>();
        Set<Long> productIds = new HashSet<>();
        products.forEach(product -> {
            List<Long> productsCategoriesIds = product.getCategories().stream().map(ProductCategory::getId).toList();
            categoriesIds.addAll(productsCategoriesIds);
            productIds.add(product.getId());
        });

        return productRepository.findRecommendedProducts(ProductStatus.ATIVO, categoriesIds, productIds)
                .stream().limit(6).map(ProductMapper::toProductResponseDto).toList();
    }

    public List<ProductResponseDto> findProductsSummary(String name, Integer maxValue, String category) {
        return productRepository
                .findWithFilter(name, ProductStatus.ATIVO, maxValue, category).stream()
                .map(ProductMapper::toProductResponseDto).toList();
    }

    public ProductResponseDto findProduct(Long id) {
        return ProductMapper.toProductResponseDto(findById(id));
    }

    public AdminProductResponseDto findProductToAdminResponseDto(Long id){
        Product product = findById(id);
        List<ProductCategoryResponseDto> categories = product.getCategories().stream().map(category -> new ProductCategoryResponseDto(category.getId(), category.getName())).toList();
        return ProductMapper.toAdminProductResponseDto(product, categories);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public Product updateQuantity(Long id, Short quantity) {
        if(quantity < 0) throw new IllegalArgumentException("Quantidade deve ser maior do que zero");
        Product product;

        try {
             product = findByIdAndStatus(id, ProductStatus.ATIVO);
        }catch (EntityNotFoundException e){
            throw new ApiException(e.getMessage());
        }

        int availableStock = product.getStockQuantity() - product.getBlockedQuantity();
        if(availableStock < quantity){
            throw new ApiException("Estoque insuficiente");
        }
        product.setBlockedQuantity(product.getBlockedQuantity() + quantity);
        return productRepository.save(product);
    }

    public void returnBlockedProductQuantity(Long id, Short quantity){
        if(quantity < 0) throw new IllegalArgumentException("Quantidade deve ser maior do que zero");
        Product product = findById(id);
        if(product.getBlockedQuantity() < quantity) throw new IllegalArgumentException("Erro no estoque");
        product.setBlockedQuantity(product.getBlockedQuantity() - quantity);
        productRepository.save(product);
    }

    public void deleteClient(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteClient'");
    }

    private Product findByIdAndStatus(Long id, ProductStatus status){
        return productRepository.findByIdAndStatus(id, status).orElseThrow(() -> new EntityNotFoundException("Produto não foi encontrado"));
    }
}

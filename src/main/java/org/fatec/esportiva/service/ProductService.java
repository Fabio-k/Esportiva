package org.fatec.esportiva.service;

import java.util.List;
import java.util.Optional;

import org.fatec.esportiva.entity.CartItem;
import org.fatec.esportiva.entity.Order;
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

    public List<ProductResponseDto> getProductsSummary(){
        return  productRepository.findAllByStatus(ProductStatus.ATIVO)
                .stream().map(ProductMapper::toProductResponseDto).toList();
    }

    public List<ProductResponseDto> findProductsSummary(String name, Integer maxValue, String category) {
        return productRepository
                .findWithFilter(name, ProductStatus.ATIVO, maxValue, category).stream()
                .map(ProductMapper::toProductResponseDto).toList();
    }

    public Optional<Product> findProduct2(Long id) {
        return productRepository.findById(id);
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
        if(quantity < 0) throw new IllegalArgumentException("Quantidade deve ser maior do que zero");
        Product product = findById(id);
        int availableStock = product.getStockQuantity() - product.getBlockedQuantity();
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
        if(quantity < 0) throw new IllegalArgumentException("Quantidade deve ser maior do que zero");
        Product product = findById(id);
        if(product.getBlockedQuantity() < quantity) throw new IllegalArgumentException("Erro no estoque");
        product.setBlockedQuantity(product.getBlockedQuantity() - quantity);
        productRepository.save(product);
    }

    public void updateQuantityAfterPurchase(List<Order> orders) {
        orders.forEach(order -> {
            Product product = order.getProduct();
            Product product1 = findById(product.getId());
            if(product1.getStockQuantity() < order.getQuantity() || order.getQuantity() > product.getBlockedQuantity()){
                throw new RuntimeException("Conflito na quantidade de itens comprados");
            }
            product1.setStockQuantity(product.getStockQuantity() - order.getQuantity());
            product1.setBlockedQuantity(product1.getBlockedQuantity() - order.getQuantity());
            productRepository.save(product1);
        });
    }
    public void deleteClient(Optional<Product> product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteClient'");
    }

}

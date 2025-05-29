package org.fatec.esportiva.integration.cart;

import org.fatec.esportiva.entity.product.PricingGroup;
import org.fatec.esportiva.entity.product.Product;
import org.fatec.esportiva.entity.product.ProductCategory;
import org.fatec.esportiva.entity.product.ProductStatus;
import org.fatec.esportiva.integration.Integration;
import org.fatec.esportiva.repository.PricingGroupRepository;
import org.fatec.esportiva.repository.ProductCategoryRepository;
import org.fatec.esportiva.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserAddProductToCart extends Integration {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private PricingGroupRepository pricingGroupRepository;

    @Test
    void shouldFailDueToBeingUnauthenticated() throws Exception{
        String requestBody = "{ \"id\":1, \"quantity\": 3 }";
        mockMvc.perform(post("/api/cart/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // @traceto(RF0031)
    @Test
    @DisplayName("user can add a product to his cart")
    @WithMockUser(username = "carlos@gmail.com", roles = "USER")
    void shouldReturnOkWithProducts() throws Exception{
        String requestBody = "{ \"id\":1, \"quantity\": 3 }";
        mockMvc.perform(post("/api/cart/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(3))
                .andExpect(jsonPath("$.product.name").value("Bola de Vôlei Mikasa 350VW"))
                .andExpect(jsonPath("$.product.quantity").value(15))
        ;
    }

    @Test
    @WithMockUser(username = "carlos@gmail.com", roles = "USER")
    void shouldReturnErrorDueToNegativeValue() throws Exception{
        String requestBody = "{ \"id\":1, \"quantity\": -1 }";
        mockMvc.perform(post("/api/cart/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Quantidade deve ser maior do que 0"))
        ;
    }

    @Test
    @WithMockUser(username = "carlos@gmail.com", roles = "USER")
    void shouldReturnErrorDueToZeroQuantity() throws Exception{
        String requestBody = "{ \"id\":1, \"quantity\": 0 }";
        mockMvc.perform(post("/api/cart/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Quantidade deve ser maior do que 0"))
        ;
    }

    @Test
    @WithMockUser(username = "carlos@gmail.com", roles = "USER")
    void shouldReturnErrorMoreThanAvailable() throws Exception{
        String requestBody = "{ \"id\":1, \"quantity\": 10000 }";
        mockMvc.perform(post("/api/cart/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Estoque insuficiente"))
        ;
    }

    @Test
    @WithMockUser(username = "carlos@gmail.com", roles = "USER")
    void shouldReturnErrorInactivatedProduct() throws Exception{
        PricingGroup pricingGroup = pricingGroupRepository.save(PricingGroup.builder()
                .name("high")
                .profitMargin(BigDecimal.valueOf(30))
                .build());
        ProductCategory category = ProductCategory.builder()
                .name("tenis")
                .build();
        Product inactivatedProduct = Product.builder()
                .name("Tenis racket")
                .stockQuantity(20)
                .blockedQuantity(0)
                .costValue(BigDecimal.valueOf(20))
                .description("a racket")
                .entryDate(LocalDate.now())
                .image("")
                .status(ProductStatus.INATIVO)
                .categories(List.of(category))
                .pricingGroup(pricingGroup)
                .inactivationJustification("")
                .build();
        inactivatedProduct = productRepository.save(inactivatedProduct);
        String requestBody = String.format("{ \"id\":%d, \"quantity\": 5 }", inactivatedProduct.getId());
        mockMvc.perform(post("/api/cart/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Produto não foi encontrado"))
        ;
    }

    @Test
    @WithMockUser(username = "carlos@gmail.com", roles = "USER")
    void shouldReturnErrorOutOfStock() throws Exception{
        PricingGroup pricingGroup = pricingGroupRepository.save(PricingGroup.builder()
                .name("high")
                .profitMargin(BigDecimal.valueOf(30))
                .build());
        ProductCategory category = ProductCategory.builder()
                .name("tenis")
                .build();
        Product outOfStockProduct = Product.builder()
                .name("Tenis racket")
                .stockQuantity(10)
                .blockedQuantity(10)
                .costValue(BigDecimal.valueOf(20))
                .description("a racket")
                .entryDate(LocalDate.now())
                .image("")
                .status(ProductStatus.ATIVO)
                .categories(List.of(category))
                .pricingGroup(pricingGroup)
                .inactivationJustification("")
                .build();
        outOfStockProduct = productRepository.save(outOfStockProduct);
        String requestBody = String.format("{ \"id\":%d, \"quantity\": 5 }", outOfStockProduct.getId());
        mockMvc.perform(post("/api/cart/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Estoque insuficiente"))
        ;
    }
}

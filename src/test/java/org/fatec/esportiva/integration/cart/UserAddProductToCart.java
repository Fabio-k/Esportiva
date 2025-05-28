package org.fatec.esportiva.integration.cart;

import org.fatec.esportiva.integration.Integration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserAddProductToCart extends Integration {
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
}

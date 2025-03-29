package org.fatec.esportiva.integration.cart;

import org.fatec.esportiva.integration.Integration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class userAddProductToCart extends Integration {

    @Test
    @DisplayName("should return ok with correct data on valid request")
    @WithMockUser(username = "carlos@gmail.com", roles = "USER")
    void shouldReturnOkWithProducts() throws Exception{
        String requestBody = "{ \"id\":1, \"quantity\": 3 }";
        mockMvc.perform(post("/api/cart/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].quantity").value(3))
                .andExpect(jsonPath("$.items[0].product.name").value("Bola de Vôlei Mikasa 350VW"))
                .andExpect(jsonPath("$.items[0].product.quantity").value(22))
        ;
    }
}

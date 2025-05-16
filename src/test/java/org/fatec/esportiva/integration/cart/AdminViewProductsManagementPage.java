package org.fatec.esportiva.integration.cart;

import org.fatec.esportiva.integration.Integration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminViewProductsManagementPage extends Integration {
    @Test
    @WithMockUser(username = "carlos@gmail.com", roles = "USER")
    void shouldFailDueToBeingUnauthorized() throws Exception{
        mockMvc.perform(get("/admin/products"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails(value = "lucas@esportiva.com", userDetailsServiceBeanName = "authService")
    void shouldSeeProductManagementPage() throws Exception{
        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("<button id=\"filter-submit\" type=\"submit\">Filtrar</button>")));
    }
}

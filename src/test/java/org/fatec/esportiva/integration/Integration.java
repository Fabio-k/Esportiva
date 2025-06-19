package org.fatec.esportiva.integration;

import jakarta.transaction.Transactional;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback
public abstract class Integration {
    @Autowired
    protected MockMvc mockMvc;

    // Permite carregar a chave API do arquivo '.env'
    // Esse código é o mesmo que tem na Main (Que não é executada durante os testes)
    @DynamicPropertySource
    static void loadEnvProperties(DynamicPropertyRegistry registry) {
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(entry ->
            registry.add(entry.getKey(), () -> entry.getValue())
        );
    }

    @BeforeAll
    static void setup(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

}

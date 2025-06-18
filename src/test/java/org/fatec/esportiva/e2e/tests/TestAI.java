package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import io.github.cdimascio.dotenv.Dotenv;


public class TestAI extends E2E {
    // Page Models
    private LoginPage login;
    private MainPage mainPage;

    // Permite carregar a chave API do arquivo '.env'
    // Esse código é o mesmo que tem na Main (Que não é executada durante os testes)
    @DynamicPropertySource
    static void loadEnvProperties(DynamicPropertyRegistry registry) {
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(entry ->
            registry.add(entry.getKey(), () -> entry.getValue())
        );
    }

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        mainPage = new MainPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RNF0044)
    @Test
    // @Disabled("A API do Google não responde para testes automatizados")
    void normalRecomendation() throws Exception {
        // Testa a recomendação da IA
        login.login("Carlos Silva");

        mainPage.openChatbot();
        assertEquals("Olá! Seja bem-vindo ao Esportiva! Sou sua assistente virtual. Em que posso ajudar?", mainPage.chatbotGetMessage());
        mainPage.chatbotSendMessage("Qual é a bola de vôlei mais barata da loja?");
        assertTrue(mainPage.chatbotGetMessage().contains("Bola Vôlei Penalty VP 5000 X"));
    }

    // @traceto(RNF0044)
    @Test
    // @Disabled("A API do Google não responde para testes automatizados")
    void invalidRecomendation() throws Exception {
        // Testa a recomendação da IA
        login.login("Carlos Silva");

        mainPage.openChatbot();
        assertEquals("Olá! Seja bem-vindo ao Esportiva! Sou sua assistente virtual. Em que posso ajudar?", mainPage.chatbotGetMessage());
        mainPage.chatbotSendMessage("Qual é a previsão do tempo hoje?");
        assertEquals("AI: Desculpe, só posso ajudar com dúvidas sobre os produtos à venda na loja Esportiva.", mainPage.chatbotGetMessage());
    }
}
package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.CartAllProductsPage;
import org.fatec.esportiva.e2e.pageObjects.CartIndividualProductPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "cart.product.timeoutInMinutes=1")
@TestPropertySource(properties = "cart.cleanup.intervalInMillis=1000")
public class TestCartExpiration extends E2E {

    // Spring sobrescreve o valor de tempo de resetar o carrinho para 1 minuto
    @Value("${cart.product.timeoutInMinutes}")
    private int cartTimeoutInMinutes;

    // Valor que a tarefa de limpar o carrinho tem que diminuir para executar
    // corretamente
    @Value("${cart.cleanup.intervalInMillis}")
    private int intervalInMillis;

    // Page Models
    private LoginPage login;
    private MainPage mainPage;
    private CartIndividualProductPage cartIndividualProductPage;
    private CartAllProductsPage cartAllProductsPage;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        mainPage = new MainPage(browser);
        cartIndividualProductPage = new CartIndividualProductPage(browser);
        cartAllProductsPage = new CartAllProductsPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RNF0042;RN0044;RNF0045)
    @Test
    void cartExpiration1Minute() {
        // Verifica se o carrinho é apagado em 1 minuto

        // Debug
        // System.out.println("Tempo para expirar o carrinho: " + cartTimeoutInMinutes);
        // System.out.println("Intervalo de leitura: " + intervalInMillis);

        login.login("Vanessa Von Hausten");
        mainPage.selectProduct(1);

        cartIndividualProductPage.increaseButton(3);
        cartIndividualProductPage.addProductToCart();

        // Aguarda 1 minuto para ir no carrinho
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }
        cartIndividualProductPage.goToCart();

        // Verifica se o produto não consta mais no carrinho
        assertEquals(true, cartAllProductsPage.isCartEmpty());
    }
}
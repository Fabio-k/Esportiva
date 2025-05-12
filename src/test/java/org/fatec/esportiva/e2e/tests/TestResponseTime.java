package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.CartAllProductsPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestResponseTime extends E2E {
    // Page Models
    private LoginPage login;
    private MainPage mainPage;
    private CartAllProductsPage cartAllProductsPage;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        mainPage = new MainPage(browser);
        cartAllProductsPage = new CartAllProductsPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RNF0011)
    @Test
    void responseTimeOneSecond() {
        // Testa se as consultas do usuário retornam um tempo menor de 1 segundo
        login.login("Carlos Silva");

        long initTime = 0; // Captura o tempo inicial
        long endTime = 0; // Captura o tempo final

        // Bola de vôlei
        initTime = System.nanoTime();
        mainPage.selectProduct(1);
        endTime = System.nanoTime();
        cartAllProductsPage.returnMainPage();
        assertTrue(getElapsedTime(initTime, endTime) < 1000);

        // Kit de marcação
        initTime = System.nanoTime();
        mainPage.selectProduct(6);
        endTime = System.nanoTime();
        cartAllProductsPage.returnMainPage();
        assertTrue(getElapsedTime(initTime, endTime) < 1000);

        // Verifica com o mesmo produto novamente para avaliar repetibilidade
        // Bola de vôlei
        initTime = System.nanoTime();
        mainPage.selectProduct(1);
        endTime = System.nanoTime();
        cartAllProductsPage.returnMainPage();
        assertTrue(getElapsedTime(initTime, endTime) < 1000);

        // Manguitos
        initTime = System.nanoTime();
        mainPage.selectProduct(8);
        endTime = System.nanoTime();
        cartAllProductsPage.returnMainPage();
        assertTrue(getElapsedTime(initTime, endTime) < 1000);
    }

    private long getElapsedTime(long initTime, long endTime) {
        if (endTime < initTime) {
            throw new IllegalArgumentException("Os parâmetros de tempo inicial e final estão invertidos");
        }
        // Divide por '1_000_000' para converter para milissegundos
        return (endTime - initTime) / 1_000_000;
    }
}
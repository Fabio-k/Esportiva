package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestLogin extends E2E {
    // Page Models
    private LoginPage login;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    @Test
    void selectNoneUser() {
        login.login("Selecione o usuário");

        assertTrue(login.getErrorMessage());
        sleepForVisualization();
    }
}

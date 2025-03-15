package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLogin extends E2E {

    @BeforeEach
    void beforeEach() {
        browser.get(baseUrl + "/login");
    }

    @Test
    void selectNoneUser() {
        LoginPage login = new LoginPage(browser);

        login.login("Selecione o usuário");

        assertTrue(login.getErrorMessage());
    }
}

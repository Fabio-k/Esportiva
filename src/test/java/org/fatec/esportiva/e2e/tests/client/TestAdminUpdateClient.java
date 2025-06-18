package org.fatec.esportiva.e2e.tests.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.UserDashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.UserFormPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAdminUpdateClient extends E2E {
    // Page Models
    private LoginPage login;
    private UserDashboardPage dashboard;
    private UserFormPage userForm;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        dashboard = new UserDashboardPage(browser);
        userForm = new UserFormPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RF0022;RF0034)
    @Test
    void adminUpdateUser() {
        login.login("Fábio");
        dashboard.editUser(3);

        // Atualiza perfil do usuário
        userForm.setName("Carla Santos");
        userForm.setTelephone("11974441237");
        userForm.setEmail("vanessinha.v.h@uol.com.br");
        sleepForVisualization();
        userForm.clickSaveUser();

        // Verifica se o usuário consta na tabela
        assertEquals(dashboard.getUserName(3), "Carla Santos");
        assertEquals(dashboard.getUserCpf(3), "94551842060");
        assertEquals(dashboard.getUserDateBirth(3), "01/11/2001");
        assertEquals(dashboard.getUserGender(3), "Feminino");
        assertEquals(dashboard.getUserTelephone(3), "11974441237");
        assertEquals(dashboard.getUserEmail(3), "vanessinha.v.h@uol.com.br");
        assertEquals(dashboard.getUserStatus(3), "Inativo");

        sleepForVisualization();
    }

    // @traceto(RF0022)
    @Test
    void adminUpdateUserWithFail() {
        login.login("Fábio");
        dashboard.editUser(3);

        // Atualiza perfil do usuário
        userForm.setTelephone("");
        userForm.setEmail("vanessinha.v.h@uol.com.br");

        // Verifica se dá erro com telefone vazio
        sleepForVisualization();
        userForm.clickSaveUser();
        assertTrue(userForm.isErrorMessagePresent("O número de telefone não pode ficar em branco"));

        // Corrige o telefone
        userForm.setTelephone("11974441237");
        sleepForVisualization();
        userForm.clickSaveUser();

        // Verifica se o usuário consta na tabela
        assertEquals(dashboard.getUserName(3), "Vanessa Von Hausten");
        assertEquals(dashboard.getUserCpf(3), "94551842060");
        assertEquals(dashboard.getUserDateBirth(3), "01/11/2001");
        assertEquals(dashboard.getUserGender(3), "Feminino");
        assertEquals(dashboard.getUserTelephone(3), "11974441237");
        assertEquals(dashboard.getUserEmail(3), "vanessinha.v.h@uol.com.br");
        assertEquals(dashboard.getUserStatus(3), "Inativo");

        sleepForVisualization();
    }


    // @traceto(RF0025)
    @Test
    void adminCheckTransactionsHistory() {
        login.login("Fábio");
        dashboard.editUser(1);

        // Verifica se o histórico do usuário consta no seu perfil
        assertEquals("25 dez. 2024", userForm.getTransactionDate(9));
        assertEquals("Entregue", userForm.getTransactionStatus(9));
        sleepForVisualization();
    }
}

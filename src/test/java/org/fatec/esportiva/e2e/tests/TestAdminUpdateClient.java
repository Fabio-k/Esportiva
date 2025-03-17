package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.DashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.UserFormPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAdminUpdateClient extends E2E {
    // Page Models
    private LoginPage login;
    private DashboardPage dashboard;
    private UserFormPage userForm;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver();
        browser.get(baseUrl);
        login = new LoginPage(browser);
        dashboard = new DashboardPage(browser);
        userForm = new UserFormPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    @Test
    void adminUpdateUser() {
        login.login("Fábio");
        dashboard.editUser(3);

        // Atualiza perfil do usuário
        userForm.setTelephone("11974441237");
        userForm.setEmail("vanessinha.v.h@uol.com.br");
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
}

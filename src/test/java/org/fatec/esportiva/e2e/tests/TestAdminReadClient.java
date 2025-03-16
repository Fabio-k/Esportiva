package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.DashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAdminReadClient extends E2E {
    // Page Models
    private LoginPage login;
    private DashboardPage dashboard;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver();
        browser.get(baseUrl);
        login = new LoginPage(browser);
        dashboard = new DashboardPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    @Test
    void adminReadTable() {
        login.login("Fábio");

        // Carlos
        assertEquals(dashboard.getUserName(1), "Carlos Silva");
        assertEquals(dashboard.getUserCpf(1), "43757832060");
        assertEquals(dashboard.getUserDateBirth(1), "30/03/1986");
        assertEquals(dashboard.getUserGender(1), "Masculino");
        assertEquals(dashboard.getUserTelephone(1), "11945653333");
        assertEquals(dashboard.getUserEmail(1), "carlos@gmail.com");
        assertEquals(dashboard.getUserStatus(1), "Ativo");

        // Mariana
        assertEquals(dashboard.getUserName(2), "Mariana Duarte");
        assertEquals(dashboard.getUserCpf(2), "71374904090");
        assertEquals(dashboard.getUserDateBirth(2), "15/02/1971");
        assertEquals(dashboard.getUserGender(2), "Feminino");
        assertEquals(dashboard.getUserTelephone(2), "11974526335");
        assertEquals(dashboard.getUserEmail(2), "marina.duarte@outlook.com");
        assertEquals(dashboard.getUserStatus(2), "Ativo");

        // Vanessa
        assertEquals(dashboard.getUserName(3), "Vanessa Von Hausten");
        assertEquals(dashboard.getUserCpf(3), "94551842060");
        assertEquals(dashboard.getUserDateBirth(3), "01/11/2001");
        assertEquals(dashboard.getUserGender(3), "Feminino");
        assertEquals(dashboard.getUserTelephone(3), "11932301004");
        assertEquals(dashboard.getUserEmail(3), "vanessa123@terra.com.br");
        assertEquals(dashboard.getUserStatus(3), "Inativo");
    }

    @Test
    void adminOneFilter() {
        login.login("Fábio");
        dashboard.openFilter();
        dashboard.setFilterName("Carlos");
        dashboard.ApplyFilter();

        // Carlos
        assertEquals(dashboard.getUserName(1), "Carlos Silva");
        assertEquals(dashboard.getUserCpf(1), "43757832060");
        assertEquals(dashboard.getUserDateBirth(1), "30/03/1986");
        assertEquals(dashboard.getUserGender(1), "Masculino");
        assertEquals(dashboard.getUserTelephone(1), "11945653333");
        assertEquals(dashboard.getUserEmail(1), "carlos@gmail.com");
        assertEquals(dashboard.getUserStatus(1), "Ativo");

        // Mariana
        assertEquals(dashboard.getUserName(2), null);
        assertEquals(dashboard.getUserCpf(2), null);
        assertEquals(dashboard.getUserDateBirth(2), null);
        assertEquals(dashboard.getUserGender(2), null);
        assertEquals(dashboard.getUserTelephone(2), null);
        assertEquals(dashboard.getUserEmail(2), null);
        assertEquals(dashboard.getUserStatus(2), null);

        // Vanessa
        assertEquals(dashboard.getUserName(3), null);
        assertEquals(dashboard.getUserCpf(3), null);
        assertEquals(dashboard.getUserDateBirth(3), null);
        assertEquals(dashboard.getUserGender(3), null);
        assertEquals(dashboard.getUserTelephone(3), null);
        assertEquals(dashboard.getUserEmail(3), null);
        assertEquals(dashboard.getUserStatus(3), null);
    }

    @Test
    void adminTwoFilter() {
        login.login("Fábio");
        dashboard.openFilter();
        dashboard.setFilterGender(Gender.FEMININO);
        dashboard.setFilterStatus(UserStatus.ATIVO);
        dashboard.ApplyFilter();

        // Carlos
        assertEquals(dashboard.getUserName(1), null);
        assertEquals(dashboard.getUserCpf(1), null);
        assertEquals(dashboard.getUserDateBirth(1), null);
        assertEquals(dashboard.getUserGender(1), null);
        assertEquals(dashboard.getUserTelephone(1), null);
        assertEquals(dashboard.getUserEmail(1), null);
        assertEquals(dashboard.getUserStatus(1), null);

        // Mariana
        assertEquals(dashboard.getUserName(2), "Mariana Duarte");
        assertEquals(dashboard.getUserCpf(2), "71374904090");
        assertEquals(dashboard.getUserDateBirth(2), "15/02/1971");
        assertEquals(dashboard.getUserGender(2), "Feminino");
        assertEquals(dashboard.getUserTelephone(2), "11974526335");
        assertEquals(dashboard.getUserEmail(2), "marina.duarte@outlook.com");
        assertEquals(dashboard.getUserStatus(2), "Ativo");

        // Vanessa
        assertEquals(dashboard.getUserName(3), null);
        assertEquals(dashboard.getUserCpf(3), null);
        assertEquals(dashboard.getUserDateBirth(3), null);
        assertEquals(dashboard.getUserGender(3), null);
        assertEquals(dashboard.getUserTelephone(3), null);
        assertEquals(dashboard.getUserEmail(3), null);
        assertEquals(dashboard.getUserStatus(3), null);
    }
}

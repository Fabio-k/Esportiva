package org.fatec.esportiva.e2e.tests;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.UserDashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class TestAdminReadClient extends E2E {
    private LoginPage login;
    private UserDashboardPage dashboard;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        dashboard = new UserDashboardPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RF0024)
    @Test
    void adminReadTable() {
        login.login("Fábio");

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

        assertEquals(dashboard.getUserName(3), "Vanessa Von Hausten");
        assertEquals(dashboard.getUserCpf(3), "94551842060");
        assertEquals(dashboard.getUserDateBirth(3), "01/11/2001");
        assertEquals(dashboard.getUserGender(3), "Feminino");
        assertEquals(dashboard.getUserTelephone(3), "11932301004");
        assertEquals(dashboard.getUserEmail(3), "vanessa123@terra.com.br");
        assertEquals(dashboard.getUserStatus(3), "Inativo");
    }

    // @traceto(RF0024)
    @Test
    void adminOneFilter() {
        login.login("Fábio");
        dashboard.openFilter();
        dashboard.setFilterName("Carlos");
        dashboard.ApplyFilter();

        assertEquals(dashboard.getUserName(1), "Carlos Silva");
        assertEquals(dashboard.getUserCpf(1), "43757832060");
        assertEquals(dashboard.getUserDateBirth(1), "30/03/1986");
        assertEquals(dashboard.getUserGender(1), "Masculino");
        assertEquals(dashboard.getUserTelephone(1), "11945653333");
        assertEquals(dashboard.getUserEmail(1), "carlos@gmail.com");
        assertEquals(dashboard.getUserStatus(1), "Ativo");

        assertEquals(dashboard.getUsersCount(), 1);

        sleepForVisualization();
    }

    // @traceto(RF0024)
    @Test
    void adminTwoFilter() {
        login.login("Fábio");
        dashboard.openFilter();
        dashboard.setFilterGender(Gender.FEMININO);
        dashboard.setFilterStatus(UserStatus.ATIVO);
        dashboard.ApplyFilter();

        assertEquals(dashboard.getUserName(2), "Mariana Duarte");
        assertEquals(dashboard.getUserCpf(2), "71374904090");
        assertEquals(dashboard.getUserDateBirth(2), "15/02/1971");
        assertEquals(dashboard.getUserGender(2), "Feminino");
        assertEquals(dashboard.getUserTelephone(2), "11974526335");
        assertEquals(dashboard.getUserEmail(2), "marina.duarte@outlook.com");
        assertEquals(dashboard.getUserStatus(2), "Ativo");

        assertEquals(dashboard.getUsersCount(), 1);

        sleepForVisualization();
    }
}

package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.DashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAdminDeleteClient extends E2E {
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
    void adminDeleteClient() {
        login.login("Fábio");
        dashboard.deleteUser(2, true);
        assertEquals(dashboard.getUserName(2), null);
        sleepForVisualization();
    }

    @Test
    void adminNotDeleteClient() {
        login.login("Fábio");
        dashboard.deleteUser(1, false);
        assertEquals(dashboard.getUserName(1), "Carlos Silva");
        sleepForVisualization();
    }
}

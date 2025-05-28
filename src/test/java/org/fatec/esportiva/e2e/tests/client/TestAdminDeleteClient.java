package org.fatec.esportiva.e2e.tests.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.UserDashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAdminDeleteClient extends E2E {
    // Page Models
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

    // @traceto(RF0023)
    @Test
    void adminDeleteClient() {
        login.login("Fábio");
        dashboard.deleteUser(2, true);
        assertFalse(dashboard.isUserPresent(2));
        sleepForVisualization();
    }

    // @traceto(RF0023)
    @Test
    void adminNotDeleteClient() {
        login.login("Fábio");
        dashboard.deleteUser(1, false);
        assertEquals(dashboard.getUserName(1), "Carlos Silva");
        sleepForVisualization();
    }
}

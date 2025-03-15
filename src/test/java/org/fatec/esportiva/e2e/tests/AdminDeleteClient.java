package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.Dashboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminDeleteClient extends E2E {

    @BeforeEach
    void beforeEach() {
        browser.get(baseUrl);
    }

    @Test
    void adminCanViewClients() {
        authenticateAs("Fábio");
        Dashboard dashboard = new Dashboard(browser);

        String obtainedName = dashboard.getUserName(1);

        assertEquals(obtainedName, "Carlos Silva");
    }
}

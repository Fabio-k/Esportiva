package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.AnalysisPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.UserDashboardPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestAnalysis extends E2E {
    // Page Models
    private LoginPage login;
    private UserDashboardPage userDashboard;
    private AnalysisPage analysisPage;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        userDashboard = new UserDashboardPage(browser);
        analysisPage = new AnalysisPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RF0055;RNF0043)
    @Test
    void salesAnalysis() {
        // Verifica se a análise exibe os dados corretamente

        login.login("Fábio");
        userDashboard.navigateAdminPages("Análise");
        analysisPage.setStartDate("01012024");
        analysisPage.setEndDate("31122024");
        analysisPage.setProduct("Bola de Vôlei Mikasa 350VW");
        
        assertEquals(24, analysisPage.getBarValue(0));
    }
}
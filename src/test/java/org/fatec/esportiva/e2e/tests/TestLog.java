package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.DeliveryDashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LogPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.UserDashboardPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestLog extends E2E {
    // Page Models
    private LoginPage login;
    private UserDashboardPage userDashboard;
    private DeliveryDashboardPage deliveryDashboard;
    private LogPage logPage;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        userDashboard = new UserDashboardPage(browser);
        deliveryDashboard = new DeliveryDashboardPage(browser);
        logPage = new LogPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RNF0012)
    @Test
    void logRegistration() {
        // O teste checa se o log registra as alterações no BD (Exemplo: Transições dos pedidos)
        login.login("Fábio");
        userDashboard.navigateAdminPages("Entrega");

        // Realiza a aprovação de uma das entregas
        deliveryDashboard.navigateDeliveryPipeline("returning");
        deliveryDashboard.transactionApprove(8, true, true);

        // Confirma a transição
        deliveryDashboard.navigateDeliveryPipeline("returned");
        assertEquals(deliveryDashboard.getTransactionClient(8), "Carlos Silva");

        // Visita a página de Log e confere se registrou a transição
        // Observação: Não usar o \r\n como resultado esperado
        deliveryDashboard.navigateAdminPages("Log");
        assertEquals("1", logPage.getId(1));
        assertEquals("fabio@esportiva.com", logPage.getUser(1));
        assertEquals("INSERT", logPage.getOperation(1));
        assertEquals("Cupom de troca\n\nID: 6\n\nValor: 1298.16", logPage.getOperationContent(1));
        assertEquals("2", logPage.getId(2));
        assertEquals("fabio@esportiva.com", logPage.getUser(2));
        assertEquals("UPDATE", logPage.getOperation(2));
        assertEquals("Pedido\n\nProduto: Bola de Vôlei Mikasa 350VW\n\nStatus: Trocado\n\nQuantidade: 2\n\nTransação: 4", logPage.getOperationContent(2));
    }
}

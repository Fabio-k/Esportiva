package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.UserDashboardPage;
import org.fatec.esportiva.e2e.pageObjects.DeliveryDashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.ProductDashboardPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAdminDeliveryPipeline extends E2E {
    // Page Models
    private LoginPage login;
    private UserDashboardPage userDashboard;
    private ProductDashboardPage productDashboard;
    private DeliveryDashboardPage deliveryDashboard;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        userDashboard = new UserDashboardPage(browser);
        productDashboard = new ProductDashboardPage(browser);
        deliveryDashboard = new DeliveryDashboardPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RF0038;RF0039;RF0041;RF0042;RF0043;RN0039;RN0040;RN0042)
    @Test
    void adminCheckTransitions() {
        // O teste checa se todas as transições acontecem corretamente
        login.login("Fábio");
        userDashboard.navigateAdminPages("Entrega");

        // Transições: Em processamento
        deliveryDashboard.transactionApprove(6, true, true);
        deliveryDashboard.transactionApprove(7, false, true);

        // Verifica as transições de "Em processamento"
        deliveryDashboard.navigateDeliveryPipeline("cancelDeliver");
        assertEquals(deliveryDashboard.getTransactionClient(7), "Mariana Duarte");
        deliveryDashboard.navigateDeliveryPipeline("inTransit");
        assertEquals(deliveryDashboard.getTransactionClient(6), "Mariana Duarte");

        // Transições: Em trânsito
        deliveryDashboard.transactionApprove(2, false, true);
        deliveryDashboard.transactionApprove(6, true, true);

        // Verifica as transições de "Em trânsito"
        deliveryDashboard.navigateDeliveryPipeline("cancelDeliver");
        assertEquals(deliveryDashboard.getTransactionClient(2), "Carlos Silva");
        deliveryDashboard.navigateDeliveryPipeline("delivered");
        assertEquals(deliveryDashboard.getTransactionClient(6), "Mariana Duarte");

        // Nota: A transição de "Entregue" será feito nos testes com foco no cliente

        // Transições: "Em troca" (Troca Solicitada)
        deliveryDashboard.navigateDeliveryPipeline("returning");
        deliveryDashboard.orderApprove(15, "reprove", true);
        deliveryDashboard.orderApprove(8, "approve", true);
        deliveryDashboard.orderApprove(18, "approve", true); // Usado para testar o aprovar+estoque

        // Verifica as transições de "Em troca"
        deliveryDashboard.navigateDeliveryPipeline("cancelRefund");
        assertEquals(deliveryDashboard.getOrderClient(15), "Vanessa Von Hausten");
        deliveryDashboard.navigateDeliveryPipeline("returned");
        assertEquals(deliveryDashboard.getOrderClient(8), "Carlos Silva");

        // Transições: "Trocado" (Troca Aceita)
        deliveryDashboard.orderApprove(10, "reprove", true);
        deliveryDashboard.orderApprove(8, "approve", true);
        deliveryDashboard.orderApprove(18, "approveStock", true);

        // Verifica as transições de "Trocado"
        deliveryDashboard.navigateDeliveryPipeline("cancelRefund");
        assertEquals(deliveryDashboard.getOrderClient(10), "Carlos Silva");
        deliveryDashboard.navigateDeliveryPipeline("returnFinished");
        assertEquals(deliveryDashboard.getOrderClient(8), "Carlos Silva");
        assertEquals(deliveryDashboard.getOrderClient(18), "Carlos Silva");

        sleepForVisualization();
    }

    // @traceto(RF0043;RF0053;RF0054)
    @Test
    void adminStockAndDelivery() {
        // O teste verifica se as transições inserem ou removem do estoque corretamente
        login.login("Fábio");
        userDashboard.navigateAdminPages("Estoque");

        // Verifica o valor inicial do estoque
        int mikasaBallStock = productDashboard.getStockQuantity(1); // Bola Mikasa
        int mikasaBallBlocked = productDashboard.getBlockedQuantity(1);
        int kitStock = productDashboard.getStockQuantity(6); // Kit de marcação
        int kitBlocked = productDashboard.getBlockedQuantity(6);
        int kneeBraceStock = productDashboard.getStockQuantity(9); // Joelheira
        int kneeBraceBlocked = productDashboard.getBlockedQuantity(9);

        // Realiza as transições no pipeline
        productDashboard.navigateAdminPages("Entrega");
        deliveryDashboard.transactionApprove(7, true, true); // Bola e joelheira
        deliveryDashboard.navigateDeliveryPipeline("returning");
        deliveryDashboard.orderApprove(8, "approve", true);
        deliveryDashboard.navigateDeliveryPipeline("returned");
        deliveryDashboard.orderApprove(8, "approve", true); // Bola Mikasa (Sem reentrada)
        deliveryDashboard.orderApprove(10, "approveStock", true); // Kit de marcação (Reentrada)

        // Verifica se o estoque variou relativo ao valor inicial
        // *Isso desacopla das atualizações no banco de dados de testes
        deliveryDashboard.navigateAdminPages("Estoque");
        // Implicitamente, o pedido de devolução da bola sem reetrada está nesse assert:
        assertEquals(mikasaBallStock - 5, productDashboard.getStockQuantity(1));
        assertEquals(mikasaBallBlocked - 5, productDashboard.getBlockedQuantity(1));
        assertEquals(kitStock + 2, productDashboard.getStockQuantity(6));
        // Como é reentrada, não afeta esse atributo:
        assertEquals(kitBlocked, productDashboard.getBlockedQuantity(6));
        assertEquals(kneeBraceStock - 5, productDashboard.getStockQuantity(9));
        assertEquals(kneeBraceBlocked - 5, productDashboard.getBlockedQuantity(9));

        sleepForVisualization();
    }

}
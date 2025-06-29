package org.fatec.esportiva.e2e.tests.shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.UserDashboardPage;
import org.fatec.esportiva.e2e.pageObjects.DeliveryDashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.MainPage;
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
    private MainPage mainPage;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        userDashboard = new UserDashboardPage(browser);
        productDashboard = new ProductDashboardPage(browser);
        deliveryDashboard = new DeliveryDashboardPage(browser);
        mainPage = new MainPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RF0038;RF0039RF0041;RF0042;RF0043;RN0039;RN0040;RN0042)
    @Test
    void adminCheckTransitions() {
        // O teste checa se todas as transições acontecem corretamente
        login.login("Fábio");
        userDashboard.navigateAdminPages("Entrega");

        // Transições: Em processamento
        deliveryDashboard.transactionApprove(6, true, false);
        deliveryDashboard.transactionApprove(7, false, false);

        // Verifica as transições de "Em processamento"
        deliveryDashboard.navigateDeliveryPipeline("cancelDeliver");
        assertEquals(deliveryDashboard.getTransactionClient(7), "Mariana Duarte");
        deliveryDashboard.navigateDeliveryPipeline("inTransit");
        assertEquals(deliveryDashboard.getTransactionClient(6), "Mariana Duarte");

        // Transições: Em trânsito
        deliveryDashboard.transactionApprove(2, false, false);
        deliveryDashboard.transactionApprove(6, true, false);

        // Verifica as transições de "Em trânsito"
        deliveryDashboard.navigateDeliveryPipeline("cancelDeliver");
        assertEquals(deliveryDashboard.getTransactionClient(2), "Carlos Silva");
        deliveryDashboard.navigateDeliveryPipeline("delivered");
        assertEquals(deliveryDashboard.getTransactionClient(6), "Mariana Duarte");

        // Nota: A transição de "Entregue" será feito nos testes com foco no cliente

        // Transições: "Em troca" (Troca Solicitada)
        deliveryDashboard.changePipelineTo("order");
        deliveryDashboard.navigateDeliveryPipeline("returning");
        deliveryDashboard.transactionApprove(15, false, false);
        deliveryDashboard.transactionApprove(8, true, false);
        deliveryDashboard.transactionApprove(18, true, false); // Usado para testar o aprovar+estoque

        // Verifica as transições de "Em troca"
        deliveryDashboard.navigateDeliveryPipeline("cancelRefund");
        assertEquals(deliveryDashboard.getOrderClient(15), "Vanessa Von Hausten");
        deliveryDashboard.navigateDeliveryPipeline("returned");
        assertEquals(deliveryDashboard.getOrderClient(8), "Carlos Silva");

        // Transições: "Trocado" (Troca Aceita)
        deliveryDashboard.transactionApprove(10, false, false);
        deliveryDashboard.transactionApprove(8, true, false);
        deliveryDashboard.transactionApprove(18, true, true);

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
        productDashboard.selectProduct(1);
        int mikasaBallStock = productDashboard.getStockQuantity(); // Bola Mikasa
        int mikasaBallBlocked = productDashboard.getBlockedQuantity();

        productDashboard.selectProduct(6);
        int kitStock = productDashboard.getStockQuantity(); // Kit de marcação
        int kitBlocked = productDashboard.getBlockedQuantity();

        productDashboard.selectProduct(9);
        int kneeBraceStock = productDashboard.getStockQuantity(); // Joelheira
        int kneeBraceBlocked = productDashboard.getBlockedQuantity();

        // Realiza as transições no pipeline
        productDashboard.navigateAdminPages("Entrega");
        deliveryDashboard.transactionApprove(7, true, false); // Bola e joelheira
        deliveryDashboard.changePipelineTo("order");
        deliveryDashboard.navigateDeliveryPipeline("returning");
        deliveryDashboard.transactionApprove(8, true, false);
        deliveryDashboard.navigateDeliveryPipeline("returned");
        deliveryDashboard.transactionApprove(8, true, false); // Bola Mikasa (Sem reentrada)
        deliveryDashboard.transactionApprove(10, true, true); // Kit de marcação (Reentrada)

        // Verifica se o estoque variou relativo ao valor inicial
        // *Isso desacopla das atualizações no banco de dados de testes
        deliveryDashboard.navigateAdminPages("Estoque");
        // Implicitamente, o pedido de devolução da bola sem reetrada está nesse assert:
        productDashboard.selectProduct(1);
        assertEquals(mikasaBallStock - 5, productDashboard.getStockQuantity());
        assertEquals(mikasaBallBlocked - 5, productDashboard.getBlockedQuantity());

        productDashboard.selectProduct(6);
        assertEquals(kitStock + 2, productDashboard.getStockQuantity());
        // Como é reentrada, não afeta esse atributo:
        assertEquals(kitBlocked, productDashboard.getBlockedQuantity());

        productDashboard.selectProduct(9);
        assertEquals(kneeBraceStock - 5, productDashboard.getStockQuantity());
        assertEquals(kneeBraceBlocked - 5, productDashboard.getBlockedQuantity());

        sleepForVisualization();
    }

    // @traceto(RNF0046)
    @Test
    void productExchangeNotification() {
        // O teste verifica se a aprovação da devolução gera uma notificação
        login.login("Fábio");

        // Aprova a devolução de um produto
        productDashboard.navigateAdminPages("Entrega");
        deliveryDashboard.changePipelineTo("order");
        deliveryDashboard.navigateDeliveryPipeline("returning");
        deliveryDashboard.transactionApprove(8, true, false);
        deliveryDashboard.navigateAdminPages("Logout");

        // Loga como o cliente e checa a notificação
        login.login("Carlos Silva");
        assertEquals("A troca do produto Bola de Vôlei Mikasa 350VW foi aceita", mainPage.getNotificationMessage(0));

        sleepForVisualization();
    }

}
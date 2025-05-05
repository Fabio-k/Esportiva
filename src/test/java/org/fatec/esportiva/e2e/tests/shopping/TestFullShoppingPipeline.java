package org.fatec.esportiva.e2e.tests.shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.CartAllProductsPage;
import org.fatec.esportiva.e2e.pageObjects.CartIndividualProductPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutAddressPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutFinalPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutPaymentPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutSummaryPage;
import org.fatec.esportiva.e2e.pageObjects.ClientHistoryPage;
import org.fatec.esportiva.e2e.pageObjects.DeliveryDashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.MainPage;
import org.fatec.esportiva.e2e.pageObjects.UserDashboardPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestFullShoppingPipeline extends E2E {
    // Page Models
    private LoginPage loginPage;
    private MainPage mainPage;
    private CartIndividualProductPage cartIndividualProductPage;
    private CartAllProductsPage cartAllProductsPage;
    private CheckoutAddressPage checkoutAddressPage;
    private CheckoutPaymentPage checkoutPaymentPage;
    private CheckoutSummaryPage checkoutSummaryPage;
    private CheckoutFinalPage checkoutFinalPage;
    private ClientHistoryPage clientHistoryPage;
    private UserDashboardPage userDashboardPage;
    private DeliveryDashboardPage deliveryDashboardPage;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        loginPage = new LoginPage(browser);
        mainPage = new MainPage(browser);
        cartIndividualProductPage = new CartIndividualProductPage(browser);
        cartAllProductsPage = new CartAllProductsPage(browser);
        checkoutAddressPage = new CheckoutAddressPage(browser);
        checkoutPaymentPage = new CheckoutPaymentPage(browser);
        checkoutSummaryPage = new CheckoutSummaryPage(browser);
        checkoutFinalPage = new CheckoutFinalPage(browser);
        clientHistoryPage = new ClientHistoryPage(browser);
        userDashboardPage = new UserDashboardPage(browser);
        deliveryDashboardPage = new DeliveryDashboardPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RF0037;RF0040;RF0044;RN0041;RN0043)
    @Test
    void normalFlowShopping() {
        // Fluxo onde simula a compra, devolução e uma nova compra usando o cupom de
        // devolução
        loginPage.login("Carlos Silva");

        // Camiseta
        mainPage.selectProduct(3);
        cartIndividualProductPage.increaseButton(2);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.returnMainPage();

        // Manguitos
        mainPage.selectProduct(8);
        cartIndividualProductPage.increaseButton(0);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.goToCart();

        // Verifica se as inserções no carrinho estão corretas
        assertEquals("Camisa Nike Dry Fit", cartAllProductsPage.getItemName(0));
        assertEquals("3", cartAllProductsPage.getItemQuantity(0));
        assertEquals("R$ 288,00", cartAllProductsPage.getItemTotalValue(0));
        assertEquals("Manguitos de Vôlei V100 VAP", cartAllProductsPage.getItemName(1));
        assertEquals("1", cartAllProductsPage.getItemQuantity(1));
        assertEquals("R$ 129,99", cartAllProductsPage.getItemTotalValue(1));
        assertEquals("R$ 417,99", cartAllProductsPage.getTotalPrice());
        cartAllProductsPage.continueShopping();

        // Verifica se os valores continuam consistentes no endereço
        assertEquals("R$ 417,99", checkoutAddressPage.getProductValue());
        assertEquals("R$ 18,00", checkoutAddressPage.getFreight());
        assertEquals("R$ 417,99", checkoutAddressPage.getTotalPrice());
        checkoutAddressPage.continueShopping();

        // Verifica se os valores continuam consistentes no cartão de crédito
        checkoutPaymentPage.selectCreditCard(0);
        checkoutPaymentPage.selectExchangeVoucher(0);
        assertEquals("R$ 417,99", checkoutPaymentPage.getProductsTotalPrice());
        assertEquals("R$ 18,00", checkoutPaymentPage.getFreightValue());
        assertEquals("R$ 435,99", checkoutPaymentPage.getTotalPrice());
        checkoutPaymentPage.continueShopping(true);

        // Verifica se os valores continuam consistentes no resumo da compra
        assertEquals("R$ 417,99", checkoutSummaryPage.getProductsTotalPrice());
        assertEquals("R$ 18,00", checkoutSummaryPage.getFreightValue());
        assertEquals("- R$ 50,00", checkoutSummaryPage.getExchangeVouchersValue());
        assertEquals("R$ 385,99", checkoutSummaryPage.getTotalPrice());
        checkoutSummaryPage.confirmShopping();

        // A compra foi finalizada com sucesso
        checkoutFinalPage.clickButtonSuccessShopping();
        assertEquals("Camisa Nike Dry Fit", clientHistoryPage.getItemName(0, 0));
        assertEquals(3, clientHistoryPage.getItemQuantity(0, 0));
        assertEquals("Manguitos de Vôlei V100 VAP", clientHistoryPage.getItemName(0, 1));
        assertEquals(1, clientHistoryPage.getItemQuantity(0, 1));
        assertEquals(getCurrentDate(), clientHistoryPage.getTransactionDate(0));
        assertEquals("Em processamento", clientHistoryPage.getTransactionStatus(0));
        clientHistoryPage.logout();

        // Troca para o fluxo do administrador
        loginPage.login("Lucas");
        userDashboardPage.navigateAdminPages("Entrega");

        // Aprova a compra que acabou de fazer (ID = 11)
        deliveryDashboardPage.transactionApprove(11, true, true);
        deliveryDashboardPage.navigateDeliveryPipeline("inTransit");
        deliveryDashboardPage.transactionApprove(11, true, true);
        deliveryDashboardPage.navigateDeliveryPipeline("delivered");
        assertEquals(deliveryDashboardPage.getTransactionClient(11), "Carlos Silva");
        deliveryDashboardPage.navigateAdminPages("Logout");

        // Usuário solicita troca de um produto
        loginPage.login("Carlos Silva");
        mainPage.linkClientHistory();
        clientHistoryPage.itemRequestReturn(0, 0, 2, true);
        assertEquals("Camisa Nike Dry Fit", clientHistoryPage.getItemName(0, 0));
        assertEquals(2, clientHistoryPage.getItemQuantity(0, 0));
        clientHistoryPage.logout();

        // Administrador segue fluxo de aprovações para devolver em cupons
        loginPage.login("Lucas");
        userDashboardPage.navigateAdminPages("Entrega");
        deliveryDashboardPage.navigateDeliveryPipeline("returning");

        // Aprova a compra que acabou de fazer (ID = 26)
        deliveryDashboardPage.orderApprove(26, "approve", true);
        deliveryDashboardPage.navigateDeliveryPipeline("returned");
        deliveryDashboardPage.orderApprove(26, "approveStock", true);
        deliveryDashboardPage.navigateDeliveryPipeline("returnFinished");
        assertEquals(deliveryDashboardPage.getOrderClient(26), "Carlos Silva");
        deliveryDashboardPage.navigateAdminPages("Logout");

        // Volta para o usuário para fazer a compra usando o cupom de devolução
        loginPage.login("Carlos Silva");

        // Bola Penalty
        mainPage.selectProduct(10);
        cartIndividualProductPage.increaseButton(1);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.goToCart();

        // Verifica se as inserções no carrinho estão corretas
        assertEquals("Bola Vôlei Penalty VP 5000 X", cartAllProductsPage.getItemName(0));
        assertEquals("2", cartAllProductsPage.getItemQuantity(0));
        assertEquals("R$ 371,76", cartAllProductsPage.getItemTotalValue(0));
        assertEquals("R$ 371,76", cartAllProductsPage.getTotalPrice());
        cartAllProductsPage.continueShopping();

        // Verifica se os valores continuam consistentes no endereço
        assertEquals("R$ 371,76", checkoutAddressPage.getProductValue());
        assertEquals("R$ 14,00", checkoutAddressPage.getFreight());
        assertEquals("R$ 371,76", checkoutAddressPage.getTotalPrice());
        checkoutAddressPage.continueShopping();

        // Verifica se os valores continuam consistentes no cartão de crédito
        checkoutPaymentPage.selectCreditCard(0);
        checkoutPaymentPage.selectExchangeVoucher(1); // Cupom de mesmo valor da devolução
        assertEquals("R$ 371,76", checkoutPaymentPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutPaymentPage.getFreightValue());
        assertEquals("R$ 385,76", checkoutPaymentPage.getTotalPrice());
        checkoutPaymentPage.continueShopping(true);

        // Verifica se os valores continuam consistentes no resumo da compra
        assertEquals("R$ 371,76", checkoutSummaryPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutSummaryPage.getFreightValue());
        assertEquals("- R$ 192,00", checkoutSummaryPage.getExchangeVouchersValue());
        assertEquals("R$ 193,76", checkoutSummaryPage.getTotalPrice());
        checkoutSummaryPage.confirmShopping();

        // A compra foi finalizada com sucesso
        checkoutFinalPage.clickButtonSuccessShopping();
        assertEquals("Bola Vôlei Penalty VP 5000 X", clientHistoryPage.getItemName(0, 0));
        assertEquals(2, clientHistoryPage.getItemQuantity(0, 0));
        assertEquals(getCurrentDate(), clientHistoryPage.getTransactionDate(0));
        assertEquals("Em processamento", clientHistoryPage.getTransactionStatus(0));

        sleepForVisualization();
    }

    private String getCurrentDate() {
        // Obter a data atual
        LocalDate currentDate = LocalDate.now();

        // Criar o formatter para o formato desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.forLanguageTag("pt-BR"));

        // Formatar a data
        return currentDate.format(formatter);
    }
}

package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.CartAllProductsPage;
import org.fatec.esportiva.e2e.pageObjects.CartIndividualProductPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutAddressPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutFinalPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutPaymentPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutSummaryPage;
import org.fatec.esportiva.e2e.pageObjects.ClientHistoryPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestClientShopping extends E2E {
    // Page Models
    private LoginPage login;
    private MainPage mainPage;
    private CartIndividualProductPage cartIndividualProductPage;
    private CartAllProductsPage cartAllProductsPage;
    private CheckoutAddressPage checkoutAddressPage;
    private CheckoutPaymentPage checkoutPaymentPage;
    private CheckoutSummaryPage checkoutSummaryPage;
    private CheckoutFinalPage checkoutFinalPage;
    private ClientHistoryPage clientHistoryPage;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        mainPage = new MainPage(browser);
        cartIndividualProductPage = new CartIndividualProductPage(browser);
        cartAllProductsPage = new CartAllProductsPage(browser);
        checkoutAddressPage = new CheckoutAddressPage(browser);
        checkoutPaymentPage = new CheckoutPaymentPage(browser);
        checkoutSummaryPage = new CheckoutSummaryPage(browser);
        checkoutFinalPage = new CheckoutFinalPage(browser);
        clientHistoryPage = new ClientHistoryPage(browser);

    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RN0033;RF0034;RN0037)
    @Test
    void normalFlowShopping() {
        // Fluxo simples, sem realizar nenhum teste de robustez
        // Somente comprar uma bola
        login.login("Carlos Silva");
        mainPage.searchProduct("Bola");
        mainPage.selectProduct(1);

        cartIndividualProductPage.increaseButton(0);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.goToCart();

        // Verifica se as inserções no carrinho estão corretas
        assertEquals("Bola de Vôlei Mikasa 350VW", cartAllProductsPage.getItemName(0));
        assertEquals("1", cartAllProductsPage.getItemQuantity(0));
        assertEquals("R$ 649,08", cartAllProductsPage.getItemTotalValue(0));
        assertEquals("R$ 649,08", cartAllProductsPage.getTotalPrice());
        cartAllProductsPage.continueShopping();

        // Verifica se os valores continuam consistentes no endereço
        assertEquals("R$ 649,08", checkoutAddressPage.getProductValue());
        assertEquals("R$ 12,00", checkoutAddressPage.getFreight());
        assertEquals("R$ 649,08", checkoutAddressPage.getTotalPrice());
        checkoutAddressPage.continueShopping();

        // Verifica se os valores continuam consistentes no cartão de crédito
        checkoutPaymentPage.selectCreditCard(0);
        checkoutPaymentPage.selectExchangeVoucher(1);
        assertEquals("R$ 649,08", checkoutPaymentPage.getProductsTotalPrice());
        assertEquals("R$ 12,00", checkoutPaymentPage.getFreightValue());
        assertEquals("R$ 661,08", checkoutPaymentPage.getTotalPrice());
        checkoutPaymentPage.continueShopping(true);

        // Verifica se os valores continuam consistentes no resumo da compra
        assertEquals("R$ 649,08", checkoutSummaryPage.getProductsTotalPrice());
        assertEquals("R$ 12,00", checkoutSummaryPage.getFreightValue());
        assertEquals("- R$ 20,00", checkoutSummaryPage.getExchangeVouchersValue());
        assertEquals("R$ 641,08", checkoutSummaryPage.getTotalPrice());
        checkoutSummaryPage.confirmShopping();

        // A compra foi finalizada com sucesso
        checkoutFinalPage.clickButtonSuccessShopping();
        assertEquals("Bola de Vôlei Mikasa 350VW", clientHistoryPage.getItemName(0, 0));
        assertEquals(1, clientHistoryPage.getItemQuantity(0, 0));
        assertEquals(getCurrentDate(), clientHistoryPage.getTransactionDate(0));
        assertEquals("Em processamento", clientHistoryPage.getTransactionStatus(0));

        sleepForVisualization();
    }

    // @traceto(RF0031;RF0032;RF0033;RF0034;RN0031;RN0037;RN0038)
    @Test
    void editShoppingWithoutAddingAddressOrCard() {
        // Fluxo onde o usuário altera a compra em várias etapas
        // Além de alterar a quantidade para testar a robustez
        // Sem adicionar um novo endereço ou cartão
        // Testa também a formatação de compras acima de R$ 1.000,00
        login.login("Carlos Silva");

        // Escolhe o tênis e valida os limites do estoque (Máximo)
        // E tenta inserir valores negativos ou zero (Mínimo)
        mainPage.selectProduct(2);
        cartIndividualProductPage.increaseButton(10);
        cartIndividualProductPage.decreaseButton(8);
        cartIndividualProductPage.increaseButton(2);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.returnMainPage();

        // Escolhe a rede de vôlei e verifica se é possível adicionar produto sem
        // estoque
        mainPage.selectProduct(5);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.returnMainPage();

        // Edita a compra dentro do carrinho
        mainPage.linkCart();
        cartAllProductsPage.increaseButton(0, 1);
        assertEquals("Tênis Adidas CourtJam", cartAllProductsPage.getItemName(0));
        assertEquals("4", cartAllProductsPage.getItemQuantity(0));
        assertEquals("R$ 1.300,00", cartAllProductsPage.getItemTotalValue(0));
        assertEquals("R$ 1.300,00", cartAllProductsPage.getTotalPrice());
        cartAllProductsPage.continueShopping();

        // Muda para o endereço do trabalho
        checkoutAddressPage.selectAddress(1);
        assertEquals("R$ 1.300,00", checkoutAddressPage.getProductValue());
        assertEquals("R$ 26,00", checkoutAddressPage.getFreight());
        assertEquals("R$ 1.326,00", checkoutAddressPage.getTotalPrice());
        checkoutAddressPage.continueShopping();

        // Usa 2 cartões de crédito como forma de pagamento
        checkoutPaymentPage.selectCreditCard(0);
        checkoutPaymentPage.selectCreditCard(1); // Cartão inválido
        assertEquals("R$ 1.300,00", checkoutPaymentPage.getProductsTotalPrice());
        assertEquals("R$ 26,00", checkoutPaymentPage.getFreightValue());
        assertEquals("R$ 1.326,00", checkoutPaymentPage.getTotalPrice());
        checkoutPaymentPage.continueShopping(true);

        // Verifica o resumo da compra
        assertEquals("R$ 1.300,00", checkoutSummaryPage.getProductsTotalPrice());
        assertEquals("R$ 26,00", checkoutSummaryPage.getFreightValue());
        assertEquals("R$ 1.300,00", checkoutSummaryPage.getTotalPrice());
        checkoutSummaryPage.confirmShopping();

        // A compra falhou devido ao cartão de crédito inválido
        checkoutFinalPage.clickButtonFailShopping();

        sleepForVisualization();
    }

    private String getCurrentDate() {
        // Obter a data atual
        LocalDate currentDate = LocalDate.now();

        // Criar o formatter para o formato desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        // Formatar a data
        return currentDate.format(formatter);
    }
}
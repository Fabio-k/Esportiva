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
import org.fatec.esportiva.e2e.pageObjects.CheckoutSplitCardsPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutSummaryPage;
import org.fatec.esportiva.e2e.pageObjects.ClientHistoryPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestClientPayment extends E2E {
    // Page Models
    private LoginPage login;
    private MainPage mainPage;
    private CartIndividualProductPage cartIndividualProductPage;
    private CartAllProductsPage cartAllProductsPage;
    private CheckoutAddressPage checkoutAddressPage;
    private CheckoutPaymentPage checkoutPaymentPage;
    private CheckoutSplitCardsPage checkoutSplitCardsPage;
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
        checkoutSplitCardsPage = new CheckoutSplitCardsPage(browser);
        checkoutSummaryPage = new CheckoutSummaryPage(browser);
        checkoutFinalPage = new CheckoutFinalPage(browser);
        clientHistoryPage = new ClientHistoryPage(browser);

    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RN0034)
    @Test
    void multipleCreditCards() {
        // Ao dividir a compra entre dois cartões sem cupons, um deles não pode ter menos de R$ 10,00
        login.login("Mariana Duarte");
        mainPage.selectProduct(4);

        cartIndividualProductPage.increaseButton(0);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.goToCart();

        // Verifica se as inserções no carrinho estão corretas
        assertEquals("Caneleira Puma Pro", cartAllProductsPage.getItemName(0));
        assertEquals("1", cartAllProductsPage.getItemQuantity(0));
        assertEquals("R$ 49,50", cartAllProductsPage.getItemTotalValue(0));
        assertEquals("R$ 49,50", cartAllProductsPage.getTotalPrice());
        cartAllProductsPage.continueShopping();

        // Verifica se os valores continuam consistentes no endereço
        assertEquals("R$ 49,50", checkoutAddressPage.getProductValue());
        assertEquals("R$ 14,00", checkoutAddressPage.getFreight());
        assertEquals("R$ 49,50", checkoutAddressPage.getTotalPrice());
        checkoutAddressPage.continueShopping();

        // Verifica se os valores continuam consistentes no cartão de crédito
        checkoutPaymentPage.selectCreditCard(0);
        checkoutPaymentPage.selectCreditCard(1);
        assertEquals("R$ 49,50", checkoutPaymentPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutPaymentPage.getFreightValue());
        assertEquals("R$ 63,50", checkoutPaymentPage.getTotalPrice());
        checkoutPaymentPage.continueShopping(true);

        // Um dos cartões tem valor menor que R$ 10,00
        checkoutSplitCardsPage.setCreditCardValue(0, "5351");
        checkoutSplitCardsPage.setCreditCardValue(1, "999");
        checkoutSplitCardsPage.continueShopping(false);

        // Redistribui o valor para R$ 10,00
        checkoutSplitCardsPage.setCreditCardValue(0, "5350");
        checkoutSplitCardsPage.setCreditCardValue(1, "1000");
        checkoutSplitCardsPage.continueShopping(true);

        // Verifica se os valores continuam consistentes no resumo da compra
        assertEquals("R$ 49,50", checkoutSummaryPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutSummaryPage.getFreightValue());
        assertEquals("R$ 63,50", checkoutSummaryPage.getTotalPrice());
        checkoutSummaryPage.confirmShopping();

        // A compra foi finalizada com sucesso
        checkoutFinalPage.clickButtonSuccessShopping();
        assertEquals("Caneleira Puma Pro", clientHistoryPage.getItemName(0, 0));
        assertEquals(1, clientHistoryPage.getItemQuantity(0, 0));
        assertEquals(getCurrentDate(), clientHistoryPage.getTransactionDate(0));
        assertEquals("Em processamento", clientHistoryPage.getTransactionStatus(0));

        sleepForVisualization();
    }

    // @traceto(RN0035)
    @Test
    void exchangeVoucherAndOneCreditCard() {
        // Usa um cupom que gera uma diferença menor que R$ 10,00
        // Verifica se dá para comprar com 1 cartão
        login.login("Mariana Duarte");
        mainPage.selectProduct(4);

        cartIndividualProductPage.increaseButton(0);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.goToCart();

        // Verifica se as inserções no carrinho estão corretas
        assertEquals("Caneleira Puma Pro", cartAllProductsPage.getItemName(0));
        assertEquals("1", cartAllProductsPage.getItemQuantity(0));
        assertEquals("R$ 49,50", cartAllProductsPage.getItemTotalValue(0));
        assertEquals("R$ 49,50", cartAllProductsPage.getTotalPrice());
        cartAllProductsPage.continueShopping();

        // Verifica se os valores continuam consistentes no endereço
        assertEquals("R$ 49,50", checkoutAddressPage.getProductValue());
        assertEquals("R$ 14,00", checkoutAddressPage.getFreight());
        assertEquals("R$ 49,50", checkoutAddressPage.getTotalPrice());
        checkoutAddressPage.continueShopping();

        // Verifica se os valores continuam consistentes no cartão de crédito
        checkoutPaymentPage.selectCreditCard(0);
        checkoutPaymentPage.selectExchangeVoucher(0);
        assertEquals("R$ 49,50", checkoutPaymentPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutPaymentPage.getFreightValue());
        assertEquals("R$ 63,50", checkoutPaymentPage.getTotalPrice());
        checkoutPaymentPage.continueShopping(true);

        // Verifica se os valores continuam consistentes no resumo da compra
        assertEquals("R$ 49,50", checkoutSummaryPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutSummaryPage.getFreightValue());
        assertEquals("- R$ 60,00", checkoutSummaryPage.getExchangeVouchersValue());
        assertEquals("R$ 3,50", checkoutSummaryPage.getTotalPrice());
        checkoutSummaryPage.confirmShopping();

        // A compra foi finalizada com sucesso
        checkoutFinalPage.clickButtonSuccessShopping();
        assertEquals("Caneleira Puma Pro", clientHistoryPage.getItemName(0, 0));
        assertEquals(1, clientHistoryPage.getItemQuantity(0, 0));
        assertEquals(getCurrentDate(), clientHistoryPage.getTransactionDate(0));
        assertEquals("Em processamento", clientHistoryPage.getTransactionStatus(0));

        sleepForVisualization();
    }

    // @traceto(RN0035)
    @Test
    void exchangeVoucherAndTwoCreditCards() {
        // Usa um cupom que gera uma diferença menor que R$ 10,00
        // Verifica se dá para comprar com 2 cartões
        login.login("Mariana Duarte");
        mainPage.selectProduct(4);

        cartIndividualProductPage.increaseButton(0);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.goToCart();

        // Verifica se as inserções no carrinho estão corretas
        assertEquals("Caneleira Puma Pro", cartAllProductsPage.getItemName(0));
        assertEquals("1", cartAllProductsPage.getItemQuantity(0));
        assertEquals("R$ 49,50", cartAllProductsPage.getItemTotalValue(0));
        assertEquals("R$ 49,50", cartAllProductsPage.getTotalPrice());
        cartAllProductsPage.continueShopping();

        // Verifica se os valores continuam consistentes no endereço
        assertEquals("R$ 49,50", checkoutAddressPage.getProductValue());
        assertEquals("R$ 14,00", checkoutAddressPage.getFreight());
        assertEquals("R$ 49,50", checkoutAddressPage.getTotalPrice());
        checkoutAddressPage.continueShopping();

        // Verifica se os valores continuam consistentes no cartão de crédito
        checkoutPaymentPage.selectCreditCard(0);
        checkoutPaymentPage.selectCreditCard(1);
        checkoutPaymentPage.selectExchangeVoucher(0);
        assertEquals("R$ 49,50", checkoutPaymentPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutPaymentPage.getFreightValue());
        assertEquals("R$ 63,50", checkoutPaymentPage.getTotalPrice());
        checkoutPaymentPage.continueShopping(true);

        // Testa se dá para inserir valor zero em um dos cartões
        checkoutSplitCardsPage.setCreditCardValue(0, "000");
        checkoutSplitCardsPage.setCreditCardValue(1, "350");
        checkoutSplitCardsPage.continueShopping(false);

        // Como tem 2 cartões, insere os valores nele
        checkoutSplitCardsPage.setCreditCardValue(0, "050");
        checkoutSplitCardsPage.setCreditCardValue(1, "300");
        checkoutSplitCardsPage.continueShopping(true);

        // Verifica se os valores continuam consistentes no resumo da compra
        assertEquals("R$ 49,50", checkoutSummaryPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutSummaryPage.getFreightValue());
        assertEquals("- R$ 60,00", checkoutSummaryPage.getExchangeVouchersValue());
        assertEquals("R$ 3,50", checkoutSummaryPage.getTotalPrice());
        checkoutSummaryPage.confirmShopping();

        // A compra foi finalizada com sucesso
        checkoutFinalPage.clickButtonSuccessShopping();
        assertEquals("Caneleira Puma Pro", clientHistoryPage.getItemName(0, 0));
        assertEquals(1, clientHistoryPage.getItemQuantity(0, 0));
        assertEquals(getCurrentDate(), clientHistoryPage.getTransactionDate(0));
        assertEquals("Em processamento", clientHistoryPage.getTransactionStatus(0));

        sleepForVisualization();
    }

    // @traceto(RN0036)
    @Test
    void exchangeVoucherGreaterThanShopping() {
        // Compra com um cupom maior que a compra, deve impedir a inserção de novos
        // cupons
        // Verifica se o valor excedente gera um novo cupom com a diferença
        // Isso é feito com cupom de R$ 65,00 e caneleira de R$ 49,50 (+ frete)
        login.login("Vanessa Von Hausten");
        mainPage.selectProduct(4);

        cartIndividualProductPage.increaseButton(0);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.goToCart();

        // Verifica se as inserções no carrinho estão corretas
        assertEquals("Caneleira Puma Pro", cartAllProductsPage.getItemName(0));
        assertEquals("1", cartAllProductsPage.getItemQuantity(0));
        assertEquals("R$ 49,50", cartAllProductsPage.getItemTotalValue(0));
        assertEquals("R$ 49,50", cartAllProductsPage.getTotalPrice());
        cartAllProductsPage.continueShopping();

        // Verifica se os valores continuam consistentes no endereço
        assertEquals("R$ 49,50", checkoutAddressPage.getProductValue());
        assertEquals("R$ 14,00", checkoutAddressPage.getFreight());
        assertEquals("R$ 49,50", checkoutAddressPage.getTotalPrice());
        checkoutAddressPage.continueShopping();

        // Verifica se os valores continuam consistentes no cartão de crédito
        checkoutPaymentPage.selectCreditCard(0);
        checkoutPaymentPage.selectExchangeVoucher(0);
        checkoutPaymentPage.selectExchangeVoucher(1);
        assertEquals("R$ 49,50", checkoutPaymentPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutPaymentPage.getFreightValue());
        assertEquals("R$ 63,50", checkoutPaymentPage.getTotalPrice());
        checkoutPaymentPage.continueShopping(false);

        // Remove o cupom extra
        // Reeinsere somente o cupom de R$ 65,00
        checkoutPaymentPage.selectCreditCard(0); // Desclica o cartão de crédito desnecessário
        checkoutPaymentPage.selectExchangeVoucher(1); // Desclica o cupom de R$ 20,00
        checkoutPaymentPage.continueShopping(true);

        // Verifica se os valores continuam consistentes no resumo da compra
        assertEquals("R$ 49,50", checkoutSummaryPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutSummaryPage.getFreightValue());
        assertEquals("- R$ 65,00", checkoutSummaryPage.getExchangeVouchersValue());
        assertEquals("R$ 0,00", checkoutSummaryPage.getTotalPrice());
        checkoutSummaryPage.confirmShopping();

        // A compra 1 foi finalizada com sucesso
        checkoutFinalPage.clickButtonSuccessShopping();
        assertEquals("Caneleira Puma Pro", clientHistoryPage.getItemName(0, 0));
        assertEquals(1, clientHistoryPage.getItemQuantity(0, 0));
        assertEquals(getCurrentDate(), clientHistoryPage.getTransactionDate(0));
        assertEquals("Em processamento", clientHistoryPage.getTransactionStatus(0));
        clientHistoryPage.mainPage();

        // Realiza uma nova compra com o cupom gerado pelo valor excedente
        mainPage.selectProduct(1);
        cartIndividualProductPage.increaseButton(0);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.goToCart();
        cartAllProductsPage.continueShopping();
        checkoutAddressPage.continueShopping();

        // Verifica se o novo cupom gerado da compra anterior é válido
        checkoutPaymentPage.selectCreditCard(0);
        checkoutPaymentPage.selectExchangeVoucher(1);
        assertEquals("R$ 649,08", checkoutPaymentPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutPaymentPage.getFreightValue());
        assertEquals("R$ 663,08", checkoutPaymentPage.getTotalPrice());
        checkoutPaymentPage.continueShopping(false);

        // Verifica se os valores continuam consistentes no resumo da compra
        assertEquals("R$ 649,08", checkoutSummaryPage.getProductsTotalPrice());
        assertEquals("R$ 14,00", checkoutSummaryPage.getFreightValue());
        assertEquals("- R$ 1,50", checkoutSummaryPage.getExchangeVouchersValue());
        assertEquals("R$ 661,58", checkoutSummaryPage.getTotalPrice());
        checkoutSummaryPage.confirmShopping();

        // A compra 2 foi finalizada com sucesso
        checkoutFinalPage.clickButtonSuccessShopping();
        assertEquals("Bola de Vôlei Mikasa 350VW", clientHistoryPage.getItemName(0, 0));
        assertEquals(1, clientHistoryPage.getItemQuantity(0, 0));
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
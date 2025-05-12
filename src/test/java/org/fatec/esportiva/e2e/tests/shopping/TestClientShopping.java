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
import org.fatec.esportiva.e2e.pageObjects.CheckoutNewAddressPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutNewCardPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutPaymentPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutSplitCardsPage;
import org.fatec.esportiva.e2e.pageObjects.CheckoutSummaryPage;
import org.fatec.esportiva.e2e.pageObjects.ClientHistoryPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.MainPage;
import org.fatec.esportiva.e2e.pageObjects.UserDashboardPage;
import org.fatec.esportiva.e2e.pageObjects.UserFormPage;
import org.fatec.esportiva.entity.enums.CreditCardBrand;
import org.fatec.esportiva.entity.address.ResidencyType;
import org.fatec.esportiva.entity.address.StreetType;
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
    private CheckoutNewAddressPage checkoutNewAddressPage;
    private CheckoutPaymentPage checkoutPaymentPage;
    private CheckoutNewCardPage checkoutNewCardPage;
    private CheckoutSplitCardsPage checkoutSplitCardsPage;
    private CheckoutSummaryPage checkoutSummaryPage;
    private CheckoutFinalPage checkoutFinalPage;
    private ClientHistoryPage clientHistoryPage;
    private UserDashboardPage userDashboardPage;
    private UserFormPage userFormPage;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        mainPage = new MainPage(browser);
        cartIndividualProductPage = new CartIndividualProductPage(browser);
        cartAllProductsPage = new CartAllProductsPage(browser);
        checkoutAddressPage = new CheckoutAddressPage(browser);
        checkoutNewAddressPage = new CheckoutNewAddressPage(browser);
        checkoutPaymentPage = new CheckoutPaymentPage(browser);
        checkoutNewCardPage = new CheckoutNewCardPage(browser);
        checkoutSplitCardsPage = new CheckoutSplitCardsPage(browser);
        checkoutSummaryPage = new CheckoutSummaryPage(browser);
        checkoutFinalPage = new CheckoutFinalPage(browser);
        clientHistoryPage = new ClientHistoryPage(browser);
        userDashboardPage = new UserDashboardPage(browser);
        userFormPage = new UserFormPage(browser);

    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RN0033;RF0034;RF0052;RN0037;RNF0021)
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

    // @traceto(RF0031;RF0032;RF0033;RF0034;RF0052;RN0031;RN0037;RN0038)
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
        cartIndividualProductPage.increaseButton(2); // Espera-se 3 itens aqui
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.returnMainPage(); // Devido a subtração no estoque, sobram 2

        // Edita a compra dentro do carrinho
        mainPage.linkCart();
        cartAllProductsPage.increaseButton(0, 1); // Agora há 4 tênis
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

        // Como tem 2 cartões, insere os valores nele
        checkoutSplitCardsPage.setCreditCardValue(0, "66300");
        checkoutSplitCardsPage.setCreditCardValue(1, "66300");
        checkoutSplitCardsPage.continueShopping(true);

        // Verifica o resumo da compra
        assertEquals("R$ 1.300,00", checkoutSummaryPage.getProductsTotalPrice());
        assertEquals("R$ 26,00", checkoutSummaryPage.getFreightValue());
        assertEquals("R$ 1.326,00", checkoutSummaryPage.getTotalPrice());
        checkoutSummaryPage.confirmShopping();

        // A compra falhou devido ao cartão de crédito inválido
        checkoutFinalPage.clickButtonFailShopping();

        sleepForVisualization();
    }

    // @traceto(RF0035;RF0036;RF0052)
    @Test
    void editShoppingAndAddressAndCard() {
        // Fluxo onde o cliente cria um novo endereço e cartão durante a compra
        login.login("Mariana Duarte");

        // Escolhe o tênis e valida os limites do estoque (Máximo)
        // E tenta inserir valores negativos ou zero (Mínimo)
        mainPage.selectProduct(10);
        cartIndividualProductPage.increaseButton(1);
        cartIndividualProductPage.addProductToCart();
        cartIndividualProductPage.goToCart();

        // Verifica os dados da compra
        assertEquals("Bola Vôlei Penalty VP 5000 X", cartAllProductsPage.getItemName(0));
        assertEquals("2", cartAllProductsPage.getItemQuantity(0));
        assertEquals("R$ 371,76", cartAllProductsPage.getItemTotalValue(0));
        assertEquals("R$ 371,76", cartAllProductsPage.getTotalPrice());
        cartAllProductsPage.continueShopping();

        // Usa o novo endereço
        checkoutAddressPage.newAddress();
        checkoutNewAddressPage.setAddressIdentificationPhrase("Apartamento da Praia");
        checkoutNewAddressPage.setAddressCep("74456300");
        checkoutNewAddressPage.setAddressResidenceType(ResidencyType.APARTAMENTO);
        checkoutNewAddressPage.setAddressStreetType(StreetType.RUA);
        checkoutNewAddressPage.setAddressNumber("950");
        checkoutNewAddressPage.setAddressObservation("Casa com vista para o mar");
        checkoutNewAddressPage.saveAddressCheckbox(true);
        checkoutNewAddressPage.continueShopping(true);
        // Continua a compra
        // checkoutAddressPage.selectAddress(1); // Endereço já fica selecionado
        checkoutAddressPage.continueShopping();

        // Usa 2 cartões de crédito como forma de pagamento
        checkoutPaymentPage.newCreditCard();
        checkoutNewCardPage.setCreditCardName("Mariana S. Duarte");
        checkoutNewCardPage.setCreditCardNumber("11112222333344");
        checkoutNewCardPage.setCreditCardCVV("565");
        checkoutNewCardPage.setCreditCardBrand(CreditCardBrand.MASTERCARD);
        checkoutNewCardPage.saveCreditCardCheckbox(true);
        checkoutNewCardPage.continueShopping(true);
        // Continua a compra
        // checkoutPaymentPage.selectCreditCard(1); // Cartão já fica selecionado
        checkoutPaymentPage.continueShopping(true);

        // Verifica o resumo da compra
        assertEquals("R$ 371,76", checkoutSummaryPage.getProductsTotalPrice());
        assertEquals("R$ 18,00", checkoutSummaryPage.getFreightValue());
        assertEquals("R$ 389,76", checkoutSummaryPage.getTotalPrice());
        checkoutSummaryPage.confirmShopping();

        // A compra foi finalizada com sucesso
        checkoutFinalPage.clickButtonSuccessShopping();

        // Checa se a informação foi adicionado no cadastro do usuário
        clientHistoryPage.logout();
        login.login("Fábio");
        userDashboardPage.editUser(2);

        assertEquals(false, userFormPage.getAddressCategoryResidential(1));
        assertEquals(false, userFormPage.getAddressCategoryBilling(1));
        assertEquals(true, userFormPage.getAddressCategoryShipping(1));
        assertEquals("Apartamento da Praia", userFormPage.getAddressIdentificationPhrase(1));
        assertEquals("74456-300", userFormPage.getAddressCep(1));
        assertEquals("Apartamento", userFormPage.getAddressResidenceType(1));
        assertEquals("Rua", userFormPage.getAddressStreetType(1));
        assertEquals("950", userFormPage.getAddressNumber(1));
        assertEquals("Casa com vista para o mar", userFormPage.getAddressObservation(1));

        assertEquals("Mariana S. Duarte", userFormPage.getCreditCardName(2));
        assertEquals("1111 2222 3333 44", userFormPage.getCreditCardNumber(2));
        assertEquals("565", userFormPage.getCreditCardCVV(2));
        assertEquals("MASTERCARD", userFormPage.getCreditCardBrand(2));

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
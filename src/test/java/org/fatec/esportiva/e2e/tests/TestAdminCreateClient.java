package org.fatec.esportiva.e2e.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.DashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.UserFormPage;
import org.fatec.esportiva.entity.enums.CreditCardBrand;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.PhoneType;
import org.fatec.esportiva.entity.enums.ResidencyType;
import org.fatec.esportiva.entity.enums.StreetType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAdminCreateClient extends E2E {
    // Page Models
    private LoginPage login;
    private DashboardPage dashboard;
    private UserFormPage userForm;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        dashboard = new DashboardPage(browser);
        userForm = new UserFormPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    @Test
    void adminCanCreateUser() {
        login.login("Fábio");
        dashboard.clickNewUser();

        // Perfil do usuário
        userForm.setName("Roberto");
        userForm.setGender(Gender.MASCULINO);
        userForm.setEmail("roberto@email.com");
        userForm.setCpf("77623412334");
        userForm.setDateBirth("16081990");
        userForm.setTelephoneType(PhoneType.TELEFONE);
        userForm.setTelephone("11960433210");

        // Endereço 1
        // Tenta remover o único endereço que já existe para testar robustez
        userForm.removeAddress();
        userForm.setAddressCategoryResidential(0, true);
        userForm.setAddressCategoryShipping(0, true);
        userForm.setAddressIdentificationPhrase(0, "Casa");
        userForm.setAddressCep(0, "07373020");
        userForm.setAddressResidenceType(0, ResidencyType.APARTAMENTO);
        userForm.setAddressStreetType(0, StreetType.AVENIDA);

        userForm.setAddressNumber(0, "123");
        // fillInputField(By.id("street_0"), "avenida dos bancos");
        // fillInputField(By.id("neighborhood_0"), "Alto Ipiranga");
        // fillInputField(By.id("city_0"), "Mogi das Cruzes");
        // fillInputField(By.id("state_0"), "São Paulo");
        // fillInputField(By.id("country_0"), "Brasil");

        // Endereço 2
        userForm.addAddress();
        userForm.setAddressCategoryResidential(1, false);
        userForm.setAddressCategoryBilling(1, true);
        userForm.setAddressCategoryShipping(1, false);
        userForm.setAddressIdentificationPhrase(1, "Empresa");
        userForm.setAddressCep(1, "44423939");
        userForm.setAddressResidenceType(1, ResidencyType.APARTAMENTO);
        userForm.setAddressStreetType(1, StreetType.RUA);

        userForm.setAddressNumber(1, "234");
        // fillInputField(By.id("street_1"), "rua francisco franco");
        // fillInputField(By.id("neighborhood_1"), "Jardim das Flores");
        // fillInputField(By.id("city_1"), "Belo Horizonte");
        // fillInputField(By.id("state_1"), "Minas Gerais");
        // fillInputField(By.id("country_1"), "Brasil");

        // Cartão de crédito 1
        // Tenta remover o único cartão de crédito que já existe para testar robustez
        userForm.removeCreditCard();
        userForm.setCreditCardName(0, "Roberto");
        userForm.setCreditCardNumber(0, "1234567812345678");
        userForm.setCreditCardCVV(0, "234");
        userForm.setCreditCardBrand(0, CreditCardBrand.MASTERCARD);

        // Cartão de crédito 2
        userForm.addCreditCard();
        userForm.setCreditCardName(1, "Roberto");
        userForm.setCreditCardNumber(1, "8765432187654321");
        userForm.setCreditCardCVV(1, "432");
        userForm.setCreditCardBrand(1, CreditCardBrand.VISA);

        sleepForVisualization();
        userForm.clickSaveUser();

        // Verifica se o usuário consta na tabela
        assertEquals(dashboard.getUserName(4), "Roberto");
        assertEquals(dashboard.getUserCpf(4), "77623412334");
        assertEquals(dashboard.getUserDateBirth(4), "16081990");
        assertEquals(dashboard.getUserGender(4), "Masculino");
        assertEquals(dashboard.getUserTelephone(4), "11960433210");
        assertEquals(dashboard.getUserEmail(4), "roberto@email.com");
        assertEquals(dashboard.getUserStatus(4), "Ativo");

        sleepForVisualization();
    }

    @Test
    void returnErrorsBecauseOfMissingRequiredFields() {
        login.login("Fábio");
        dashboard.clickNewUser();
        userForm.clickSaveUser();

        assertTrue(userForm.isErrorMessagePresent("O nome deve conter apenas letras e espaços"));
        assertTrue(userForm.isErrorMessagePresent("E-mail não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("CPF não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("O CPF deve conter 11 dígitos numéricos"));
        assertTrue(userForm.isErrorMessagePresent("A data de nascimento não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("O número de telefone não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("Endereço deve pertencer ao menos a um tipo de residência"));
        assertTrue(userForm.isErrorMessagePresent("Frase de identificação não deve ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("CEP não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("Nome do logradouro não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("Número não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("Número somente aceita dígitos"));
        assertTrue(userForm.isErrorMessagePresent("Bairro não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("Cidade não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("Estado não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("País não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("Nome do cliente não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("Número não pode ficar em branco"));
        assertTrue(userForm.isErrorMessagePresent("O campo deve conter um número de cartão de crédito válido"));
        assertTrue(userForm.isErrorMessagePresent("O código de segurança somente contém dígitos"));
        assertTrue(userForm.isErrorMessagePresent("Código de segurança não pode ficar em branco"));

        sleepForVisualization();
    }
}

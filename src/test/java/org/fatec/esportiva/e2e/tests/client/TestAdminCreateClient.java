package org.fatec.esportiva.e2e.tests.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fatec.esportiva.e2e.E2E;
import org.fatec.esportiva.e2e.pageObjects.UserDashboardPage;
import org.fatec.esportiva.e2e.pageObjects.LoginPage;
import org.fatec.esportiva.e2e.pageObjects.UserFormPage;
import org.fatec.esportiva.entity.enums.CreditCardBrand;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.PhoneType;
import org.fatec.esportiva.entity.address.ResidencyType;
import org.fatec.esportiva.entity.address.StreetType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestAdminCreateClient extends E2E {
    private LoginPage login;
    private UserDashboardPage dashboard;
    private UserFormPage userForm;

    @BeforeEach
    void beforeEach() {
        browser = new ChromeDriver(options);
        browser.get(baseUrl);
        login = new LoginPage(browser);
        dashboard = new UserDashboardPage(browser);
        userForm = new UserFormPage(browser);
    }

    @AfterEach
    void afterEach() {
        browser.quit();
    }

    // @traceto(RF0021;RF0026;RF0027;RNF0035)
    @Test
    void adminCanCreateUser() {
        login.login("Fábio");
        dashboard.clickNewUser();

        userForm.setName("Roberto");
        userForm.setGender(Gender.MASCULINO);
        userForm.setEmail("roberto@email.com");
        userForm.setCpf("77623412334");
        userForm.setDateBirth("16081990");
        userForm.setTelephoneType(PhoneType.TELEFONE);
        userForm.setTelephone("11960433210");

        userForm.removeAddress();
        userForm.setAddressCategoryResidential(0, true);
        userForm.setAddressCategoryShipping(0, true);
        userForm.setAddressIdentificationPhrase(0, "Casa");
        userForm.setAddressCep(0, "07373020");
        userForm.setAddressResidenceType(0, ResidencyType.APARTAMENTO);
        userForm.setAddressStreetType(0, StreetType.AVENIDA);

        userForm.setAddressNumber(0, "123");

        userForm.addAddress();
        userForm.setAddressCategoryResidential(1, false);
        userForm.setAddressCategoryBilling(1, true);
        userForm.setAddressCategoryShipping(1, false);
        userForm.setAddressIdentificationPhrase(1, "Empresa");
        userForm.setAddressCep(1, "44423939");
        userForm.setAddressResidenceType(1, ResidencyType.APARTAMENTO);
        userForm.setAddressStreetType(1, StreetType.RUA);

        userForm.setAddressNumber(1, "234");

        userForm.removeCreditCard();
        userForm.setCreditCardName(0, "Roberto");
        userForm.setCreditCardNumber(0, "1234567812345678");
        userForm.setCreditCardCVV(0, "234");
        userForm.setCreditCardBrand(0, CreditCardBrand.MASTERCARD);

        userForm.addCreditCard();
        userForm.setCreditCardName(1, "Roberto");
        userForm.setCreditCardNumber(1, "8765432187654321");
        userForm.setCreditCardCVV(1, "432");
        userForm.setCreditCardBrand(1, CreditCardBrand.VISA);

        sleepForVisualization();
        userForm.clickSaveUser();

        assertEquals(dashboard.getUserName(4), "Roberto");
        assertEquals(dashboard.getUserCpf(4), "77623412334");
        assertEquals(dashboard.getUserDateBirth(4), "16/08/1990");
        assertEquals(dashboard.getUserGender(4), "Masculino");
        assertEquals(dashboard.getUserTelephone(4), "11960433210");
        assertEquals(dashboard.getUserEmail(4), "roberto@email.com");
        assertEquals(dashboard.getUserStatus(4), "Ativo");

        sleepForVisualization();
    }

    // @traceto(RN0021;RN0022;RN0023;RN0024;RN0025;RN0026)
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

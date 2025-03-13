package org.fatec.esportiva.e2e.clients;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fatec.esportiva.e2e.E2E;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AdminCreateClient extends E2E {

    @BeforeEach
    void beforeEach() {
        browser.get(baseUrl);
    }

    public void authenticateAs(String name) {
        wait.until(ExpectedConditions.urlContains("/login"));
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submitButton")));
        selectByText(By.id("users"), name);

        submitButton.click();

        wait.until(ExpectedConditions.stalenessOf(submitButton));
    }

    @Test
    void unauthenticatedUserCannotAccessProtectedPage() {
        browser.get(baseUrl + "/admin/clients");
        WebElement body = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageText = body.getText();

        assertTrue(pageText.contains("Entrar"));
        assertFalse(pageText.contains("carlos@gmail.com"));
    }

    @Test
    void unauthorizeUserCannotAccessPage() {
        authenticateAs("Carlos Silva");
        browser.get(baseUrl + "/admin/clients");

        WebElement body = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageText = body.getText();

        assertTrue(pageText.contains("Entrar"));
        assertFalse(pageText.contains("carlos@gmail.com"));
    }

    @Test
    void adminCanViewClients() {
        authenticateAs("Fábio");

        WebElement body = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageText = body.getText();

        assertTrue(pageText.contains("Carlos Silva"));
        assertFalse(pageText.contains("Lucas"));
    }

    @Test
    void adminCanCreateUser() {
        authenticateAs("Fábio");
        WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("createClientButton")));
        createButton.click();
        fillInputField(By.id("name"), "Roberto");
        fillInputField(By.id("cpf"), "77623412334");
        fillInputField(By.id("telephone"), "11960433210");
        fillInputField(By.id("email"), "roberto@email.com");
        fillInputField(By.id("dateBirth"), "16081990");
        selectByText(By.id("telephoneType"), "TELEFONE");
        selectByText(By.id("gender"), "Masculino");

        WebElement residentialCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("residence_0")));
        residentialCheckbox.click();

        fillInputField(By.id("addressIdentificationPhrase_0"), "casa");
        fillInputField(By.id("cep_0"), "07373020");
        selectByText(By.id("residencyType_0"), "APARTAMENTO");
        selectByText(By.id("streetType_0"), "AVENIDA");
        fillInputField(By.id("street_0"), "avenida dos bancos");
        fillInputField(By.id("neighborhood_0"), "Alto Ipiranga");
        fillInputField(By.id("city_0"), "Mogi das Cruzes");
        fillInputField(By.id("state_0"), "São Paulo");
        fillInputField(By.id("country_0"), "Brasil");
        fillInputField(By.id("number_0"), "123");
        WebElement newAddressButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("addAddressButton")));
        newAddressButton.click();
        WebElement shippingCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("shipping_1")));
        shippingCheckbox.click();
        fillInputField(By.id("addressIdentificationPhrase_1"), "empresa");
        fillInputField(By.id("cep_1"), "44423939");
        selectByText(By.id("residencyType_1"), "Apartamento");
        selectByText(By.id("streetType_1"), "Rua");
        fillInputField(By.id("street_1"), "rua francisco franco");
        fillInputField(By.id("neighborhood_1"), "Jardim das Flores");
        fillInputField(By.id("city_1"), "Belo Horizonte");
        fillInputField(By.id("state_1"), "Minas Gerais");
        fillInputField(By.id("country_1"), "Brasil");
        fillInputField(By.id("number_1"), "234");

        fillInputField(By.id("creditCardName_0"), "Roberto");
        fillInputField(By.id("creditCardNumber_0"), "1234567812345678");
        fillInputField(By.id("creditCardSecurityCode_0"), "234");
        selectByText(By.id("creditCardBrand_0"), "MASTERCARD");
        WebElement newCreditCardButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.id("addCreditCardButton")));
        newCreditCardButton.click();
        fillInputField(By.id("creditCardName_1"), "Roberto");
        fillInputField(By.id("creditCardNumber_1"), "8765432187654321");
        fillInputField(By.id("creditCardSecurityCode_1"), "432");
        selectByText(By.id("creditCardBrand_1"), "VISA");

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submitButton")));
        submitButton.click();

        wait.until(ExpectedConditions.stalenessOf(submitButton));
        WebElement body = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageText = body.getText();

        assertTrue(pageText.contains("Carlos Silva"));
        assertTrue(pageText.contains("Roberto"));
        assertTrue(pageText.contains("roberto@email.com"));
        assertFalse(pageText.contains("Cadastro de Usuário"));
        assertFalse(pageText.contains("avenida dos bancos"));
    }

    @Test
    void returnErrorsBecauseOfMissingRequiredFields() {
        authenticateAs("Fábio");
        WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("createClientButton")));
        createButton.click();

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submitButton")));
        submitButton.click();

        wait.until(ExpectedConditions.stalenessOf(submitButton));
        WebElement body = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageText = body.getText();

        assertTrue(pageText.contains("Cadastro de Usuário"));
        assertTrue(pageText.contains("O nome deve conter apenas letras e espaços"));
        assertTrue(pageText.contains("E-mail não pode ficar em branco"));
        assertTrue(pageText.contains("CPF não pode ficar em branco"));
        assertTrue(pageText.contains("O CPF deve conter 11 dígitos numéricos"));
        assertTrue(pageText.contains("A data de nascimento não pode ficar em branco"));
        assertTrue(pageText.contains("O número de telefone não pode ficar em branco"));
        assertTrue(pageText.contains("Endereço deve pertencer ao menos a um tipo de residência"));
        assertTrue(pageText.contains("Frase de identificação não deve ficar em branco"));
        assertTrue(pageText.contains("CEP não pode ficar em branco"));
        assertTrue(pageText.contains("Nome do logradouro não pode ficar em branco"));
        assertTrue(pageText.contains("Número não pode ficar em branco"));
        assertTrue(pageText.contains("Número somente aceita dígitos"));
        assertTrue(pageText.contains("Bairro não pode ficar em branco"));
        assertTrue(pageText.contains("Cidade não pode ficar em branco"));
        assertTrue(pageText.contains("Estado não pode ficar em branco"));
        assertTrue(pageText.contains("País não pode ficar em branco"));
        assertTrue(pageText.contains("Nome do cliente não pode ficar em branco"));
        assertTrue(pageText.contains("Número não pode ficar em branco"));
        assertTrue(pageText.contains("O campo deve conter um número de cartão de crédito válido"));
        assertTrue(pageText.contains("O código de segurança somente contém dígitos"));
        assertTrue(pageText.contains("Código de segurança não pode ficar em branco"));
    }
}

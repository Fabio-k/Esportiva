package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;

import org.fatec.esportiva.entity.enums.CreditCardBrand;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutNewCardPage {
    @SuppressWarnings("unused")
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutNewCardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void saveCreditCardCheckbox(boolean check) {
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("saveCreditCard")));

        if (check) {
            if (!checkbox.isSelected()) {
                checkbox.click(); // Marca o checkbox
            }
        } else {
            if (checkbox.isSelected()) {
                checkbox.click(); // Desmarca o checkbox
            }
        }
    }

    public void setCreditCardName(String cardName) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("creditCardName")));

        field.clear();
        field.sendKeys(cardName);
    }

    public void setCreditCardNumber(String cardNumber) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("creditCardNumber")));

        field.clear();
        field.sendKeys(cardNumber);
    }

    public void setCreditCardCVV(String CVV) {
        WebElement field = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.id("creditCardSecurityCode")));

        field.clear();
        field.sendKeys(CVV);
    }

    public void setCreditCardBrand(CreditCardBrand brand) {
        // Encontra as opções e cria um objeto desse tipo
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("creditCardBrand")));
        Select select = new Select(dropdown);

        // Seleciona pelo texto que está visível nas opções
        select.selectByValue(brand.name());
    }

    public void continueShopping(boolean expectedPass) {
        WebElement button = driver.findElement(By.className("primaryButton"));
        String currentUrl = driver.getCurrentUrl();

        button.click();

        // Só aguarda a transição de página se espera-se que ele passe
        // Em caso de falhas não precisa aguardar
        if (expectedPass) {
            // Espera a nova página ser carregada, quando a URL atual fica inválida
            wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
        }
    }
}
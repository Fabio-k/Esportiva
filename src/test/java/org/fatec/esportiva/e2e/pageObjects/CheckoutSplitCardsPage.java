package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutSplitCardsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutSplitCardsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void setCreditCardValue(int cardIndex, String cardValue) {
        // Observação: Como o input considera também os centavos, tem que adicionar eles
        // Exemplo: R$ 427 -> 42700
        WebElement input = driver.findElement(By.cssSelector("input[data-index='" + cardIndex + "']"));
        input.sendKeys(cardValue);
    }

    public void clearCreditCardValue(int cardIndex){
        WebElement input = driver.findElement(By.cssSelector("input[data-index='" + cardIndex + "']"));
        input.clear();
    }

    public String getErrorMessage() {
        // Até o momento, somente existe uma tag <span> em todo o HTML
        WebElement message = driver.findElement(By.tagName("span"));
        return message.getText();
    }

    public void closeErrorMessage() {
        WebElement button = driver.findElement(By.className("errorMessageCloseButton"));

        button.click();
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
        // Fecha a mensagem de erro
        else {
            WebElement errorMessage = wait.until(ExpectedConditions.elementToBeClickable(By.id("errorMessageCloseButton")));
            errorMessage.click();
        }
    }
}
package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutPaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public String getCreditCardNumber(int position) {
        WebElement creditCardContainer = driver.findElement(By.id("creditCardList"));

        List<WebElement> allCreditCards = creditCardContainer.findElements(By.xpath("./div"));
        WebElement creditCard = allCreditCards.get(position);

        WebElement label = creditCard.findElement(By.tagName("label"));
        return label.getText();
    }

    public String getExchangeVoucherNumber(int position) {
        WebElement exchageVoucherContainer = driver.findElement(By.id("exchangeVoucherList"));

        List<WebElement> allExchangeVouchers = exchageVoucherContainer.findElements(By.xpath("./div"));
        WebElement exchangeVoucher = allExchangeVouchers.get(position);

        WebElement label = exchangeVoucher.findElement(By.tagName("label"));
        return label.getText();
    }

    public String getPromotionVoucherCode() {
        WebElement promotionVoucherCode = driver.findElement(By.id("discountCouponCode"));

        return promotionVoucherCode.getText();
    }

    public String getPromotionVoucherValue() {
        WebElement promotionVoucherValue = driver.findElement(By.id("discountCouponValue"));

        return promotionVoucherValue.getText();
    }

    public String getProductsTotalPrice() {
        WebElement productsTotalPrice = driver.findElement(By.id("summaryProductsTotalPrice"));

        return productsTotalPrice.getText();
    }

    public String getFreightValue() {
        WebElement freight = driver.findElement(By.id("summaryFreight"));

        return freight.getText();
    }

    public String getVouchersValue() {
        WebElement vouchers = driver.findElement(By.id("summaryVouchers"));

        return vouchers.getText();
    }

    public String getTotalPrice() {
        WebElement totalPrice = driver.findElement(By.id("summaryTotalPrice"));

        return totalPrice.getText();
    }

    public void selectCreditCard(int position) {
        WebElement creditCardContainer = driver.findElement(By.id("creditCardList"));

        List<WebElement> allCreditCards = creditCardContainer.findElements(By.xpath("./label"));

        WebElement creditCard = allCreditCards.get(position);
        //WebElement label = creditCard.findElement(By.tagName("label"));
        creditCard.click();
    }

    public void selectExchangeVoucher(int position) {
        WebElement exchageVoucherContainer = driver.findElement(By.id("exchangeVoucherList"));

        List<WebElement> allExchangeVouchers = exchageVoucherContainer.findElements(By.xpath("./label"));

        WebElement exchangeVoucher = allExchangeVouchers.get(position);
        //WebElement label = exchangeVoucher.findElement(By.tagName("label"));
        exchangeVoucher.click();
    }

    public void setPromotionVoucher(String promotionVoucher) {
        WebElement promotionVoucherInput = driver.findElement(By.id("code"));

        promotionVoucherInput.clear();
        promotionVoucherInput.sendKeys(promotionVoucher);

        WebElement button = driver.findElement(By.id("applyDiscountButton"));
        button.click();
    }

    public void closeErrorMessage() {
        WebElement button = driver.findElement(By.id("errorMessageCloseButton"));

        button.click();
    }

    public void newCreditCard() {
        // Após inserir o produto no carrinho, você pode ir até ele
        WebElement link = driver.findElement(By.linkText("Usar Novo cartão de crédito"));
        String currentUrl = driver.getCurrentUrl();

        link.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    public void continueShopping(boolean expectedPass) {
        WebElement button = driver.findElement(By.id("button-continue-shopping"));
        String currentUrl = driver.getCurrentUrl();

        button.click();

        // Só aguarda a transição de página se espera-se que ele passe
        // Em caso de falhas não precisa aguardar
        if (expectedPass) {
            // Espera a nova página ser carregada, quando a URL atual fica inválida
            wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
        }
        // Fecha a mensagem de erro
        else{
            WebElement errorMessage = wait.until(ExpectedConditions.elementToBeClickable(By.id("errorMessageCloseButton")));
            errorMessage.click();
        }
    }
}
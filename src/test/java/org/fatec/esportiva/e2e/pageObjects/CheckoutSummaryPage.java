package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutSummaryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutSummaryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
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

    public void confirmShopping() {
        WebElement button = driver.findElement(By.id("button-confirm-shopping"));
        String currentUrl = driver.getCurrentUrl();

        button.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }
}

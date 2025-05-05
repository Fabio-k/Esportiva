package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutFinalPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutFinalPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void clickButtonSuccessShopping() {
        WebElement button = driver.findElement(By.id("button-success"));
        String currentUrl = driver.getCurrentUrl();

        button.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    public void clickButtonFailShopping() {
        WebElement button = driver.findElement(By.id("button-fail"));
        String currentUrl = driver.getCurrentUrl();

        button.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }
}
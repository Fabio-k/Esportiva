package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartIndividualProduct {
    private WebDriver driver;
    private WebDriverWait wait;

    public CartIndividualProduct(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void returnMainPage() {
        // Retorna ao menu inicial
        WebElement link = driver.findElement(By.id("button-return"));
        String currentUrl = driver.getCurrentUrl();

        link.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    public void goToCart(String search) {
        // Após inserir o produto no carrinho, você pode ir até ele
        WebElement link = driver.findElement(By.linkText("Ir para o carrinho"));
        String currentUrl = driver.getCurrentUrl();

        link.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    public void decreaseButton(int clickTimes) {
        WebElement button = driver.findElement(By.id("decreaseButton"));

        for (int i = 0; i < clickTimes; i++) {
            button.click();
        }
    }

    public void increaseButton(int clickTimes) {
        WebElement button = driver.findElement(By.id("increaseButton"));

        for (int i = 0; i < clickTimes; i++) {
            button.click();
        }
    }

    public void addProductToCart() {
        // Retorna ao menu inicial
        WebElement button = driver.findElement(By.id("addProductButton"));

        String currentUrl = driver.getCurrentUrl();

        button.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    public String getProductName() {
        WebElement productName = driver.findElement(By.tagName("h1"));
        return productName.getText();
    }

}
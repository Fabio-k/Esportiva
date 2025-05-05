package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutAddressPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutAddressPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public String getProductValue() {
        WebElement productValue = driver.findElement(By.id("productsValue"));
        return productValue.getText();
    }

    public String getFreight() {
        WebElement freight = driver.findElement(By.id("freight"));
        return freight.getText();
    }

    public String getTotalPrice() {
        WebElement totalPrice = driver.findElement(By.id("totalPrice"));
        return totalPrice.getText();
    }

    public void selectAddress(int position){
        WebElement addressContainer = driver.findElement(By.id("address-list"));
        // Encontra todos os itens e retorna somente um da posição desejada
        List<WebElement> allAddress = addressContainer.findElements(By.cssSelector(".card.addressCard"));
        WebElement address = allAddress.get(position);

        address.click();
    }

    public void newAddress() {
        // Após inserir o produto no carrinho, você pode ir até ele
        WebElement link = driver.findElement(By.linkText("Usar Novo Endereço"));
        String currentUrl = driver.getCurrentUrl();

        link.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    public void continueShopping() {
        WebElement button = driver.findElement(By.id("button-continue-shopping"));
        String currentUrl = driver.getCurrentUrl();

        button.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }
}
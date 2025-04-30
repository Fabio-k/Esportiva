package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartAllProductsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CartAllProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public String getTotalPrice() {
        WebElement totalPrice = driver.findElement(By.id("totalPrice"));
        return totalPrice.getText();
    }

    public void continueShopping() {
        WebElement button = driver.findElement(By.id("button-continue-shopping"));
        String currentUrl = driver.getCurrentUrl();

        button.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    public String getItemName(int position) {
        WebElement item = findItem(position);
        WebElement pElement = item.findElement(By.tagName("p"));
        return pElement.getText();
    }

    public String getItemQuantity(int position) {
        WebElement item = findItem(position);
        WebElement button = item.findElement(By.id("quantityItem"));
        return button.getText();
    }

    public String getItemTotalValue(int position) {
        WebElement item = findItem(position);
        WebElement totalPrice = item.findElement(By.className("itemTotalPrice"));
        return totalPrice.getText();
    }

    public void increaseButton(int position, int clickTimes) {
        WebElement item = findItem(position);
        WebElement button = item.findElement(By.id("increaseButton"));

        for (int i = 0; i < clickTimes; i++) {
            button.click();
        }
        // Tempo extra para carregar os botões e o javascript
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }
    }

    public void decreaseButton(int position, int clickTimes) {
        WebElement item = findItem(position);
        WebElement button = item.findElement(By.id("decreaseButton"));

        for (int i = 0; i < clickTimes; i++) {
            button.click();
        }
        // Tempo extra para carregar os botões e o javascript
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }
    }

    public void removeButton(int position) {
        WebElement item = findItem(position);
        WebElement removeButton = item.findElement(By.xpath("//span[text()='Remover']"));

        removeButton.click();
    }

    private WebElement findItem(int position) {
        WebElement allItens = driver.findElement(By.id("itemList"));

        // Encontra todos os itens e retorna somente um da posição desejada
        List<WebElement> elementos = allItens.findElements(By.cssSelector(".itemCard"));

        return elementos.get(position);
    }

}
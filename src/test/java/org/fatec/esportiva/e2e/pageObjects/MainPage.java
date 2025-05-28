package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void logout() {
        WebElement link = driver.findElement(By.linkText("Sair"));
        link.click();
        // Espera a nova página ser carregada, quando o link fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(link));
    }

    public void searchProduct(String search) {
        WebElement searchBar = driver.findElement(By.id("searchInput"));

        searchBar.clear();
        searchBar.sendKeys(search + "\n"); // Pressiona ENTER para executar a busca

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products-container")));
    }

    public String getNotificationMessage(int position) {
        // Clica no ícone para conseguir pegar o texto das notificações
        WebElement notificationIcon = driver.findElement(By.id("notificationIcon"));
        notificationIcon.click();

        WebElement notificationContainer = driver.findElement(By.id("notificationList"));

        // Encontra todos os itens e retorna somente um da posição desejada
        List<WebElement> notificationList = notificationContainer.findElements(By.cssSelector(".notificationCard"));
        WebElement notification = notificationList.get(position);
        // O primeiro parágrafo tem a mensagem
        WebElement notificatonMessage = notification.findElement(By.tagName("p"));
        String message = notificatonMessage.getText();

        return message;
    }

    public void selectProduct(int productId) {
        WebElement link = driver.findElement(By.cssSelector("a[href='/products/" + productId + "']"));
        String currentUrl = driver.getCurrentUrl();
        link.click();
        // Espera a nova página ser carregada, quando o link fica 'inválido'
        //wait.until(ExpectedConditions.stalenessOf(link));
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    public void linkClientHistory() {
        WebElement link = driver.findElement(By.linkText("Pedidos"));
        String currentUrl = driver.getCurrentUrl();

        link.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    public void linkCart() {
        WebElement link = driver.findElement(By.id("link-cart"));
        String currentUrl = driver.getCurrentUrl();

        link.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));

    }
}
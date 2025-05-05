package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeliveryDashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public DeliveryDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    // Navega entre as páginas do administrador
    public void navigateAdminPages(String adminPageName) {
        WebElement link;

        // Procura o link pelo conteúdo dentro do texto
        if (adminPageName == "Dashboard") {
            link = driver.findElement(By.linkText("Dashboard"));

        } else if (adminPageName == "Estoque") {
            link = driver.findElement(By.linkText("Estoque"));

        } else if (adminPageName == "Entrega") {
            link = driver.findElement(By.linkText("Entrega"));

        } else if (adminPageName == "Análise") {
            link = driver.findElement(By.linkText("Análise"));

        } else if (adminPageName == "Log") {
            link = driver.findElement(By.linkText("Log"));

        } else if (adminPageName == "Logout") {
            link = driver.findElement(By.linkText("Logout"));

        } else {
            throw new IllegalArgumentException("Opção inválida: " + adminPageName);
        }

        link.click();

        // Espera a nova página ser carregada, quando o link fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(link));
    }

    // Troca entre as etapas do fluxo de entrega/devolução
    public void navigateDeliveryPipeline(String pipelineStepName) {
        WebElement link;
        String currentUrl = driver.getCurrentUrl();

        // Procura o link pelo conteúdo dentro do texto
        if (pipelineStepName == "inProcessing") {
            link = driver.findElement(By.id("link-in-processing"));

        } else if (pipelineStepName == "inTransit") {
            link = driver.findElement(By.id("link-in-transit"));

        } else if (pipelineStepName == "delivered") {
            link = driver.findElement(By.id("link-delivered"));

        } else if (pipelineStepName == "returning") {
            link = driver.findElement(By.id("link-returning"));

        } else if (pipelineStepName == "returned") {
            link = driver.findElement(By.id("link-returned"));

        } else if (pipelineStepName == "returnFinished") {
            link = driver.findElement(By.id("link-return-finished"));

        } else if (pipelineStepName == "cancelDeliver") {
            link = driver.findElement(By.id("link-cancel-deliver"));

        } else if (pipelineStepName == "cancelRefund") {
            link = driver.findElement(By.id("link-cancel-refund"));

        } else {
            throw new IllegalArgumentException("Opção inválida: " + pipelineStepName);
        }

        link.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    // Transactions (Compra como um todo)

    public String getTransactionDate(int id) {
        WebElement date = driver.findElement(By.id("purchaseDate-" + id));
        return date.getText();
    }

    public String getTransactionClient(int id) {
        WebElement client = driver.findElement(By.id("client-" + id));
        return client.getText();
    }

    public void transactionApprove(int id, Boolean approve, Boolean confirm) {
        WebElement button;

        // Escolhe se vai aprovar ou reprovar
        if (approve) {
            button = driver.findElement(By.id("approve-" + id));
        } else {
            button = driver.findElement(By.id("reprove-" + id));
        }

        button.click();

        // Confirma a opção mo pop-up
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alertButton = driver.switchTo().alert();
        if (confirm) {
            alertButton.accept();
        } else {
            alertButton.dismiss();
        }
    }

    // Orders (Itens dos pedidos)

    public String getOrderProduct(int id) {
        WebElement product = driver.findElement(By.id("product-" + id));
        return product.getText();
    }

    public String getOrderQuantity(int id) {
        WebElement quantity = driver.findElement(By.id("quantity-" + id));
        return quantity.getText();
    }

    public String getOrderClient(int id) {
        WebElement client = driver.findElement(By.id("client-" + id));
        return client.getText();
    }

    public void orderApprove(int id, String approve, Boolean confirm) {
        WebElement button;

        // Escolhe se vai aprovar ou reprovar
        if (approve == "approveStock") {
            button = driver.findElement(By.id("approve-stock-" + id));

        } else if (approve == "approve") {
            button = driver.findElement(By.id("approve-" + id));
        } else if (approve == "reprove") {
            button = driver.findElement(By.id("reprove-" + id));

        } else {
            throw new IllegalArgumentException("Opção inválida: " + approve);
        }

        button.click();

        // Confirma a opção mo pop-up
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alertButton = driver.switchTo().alert();
        if (confirm) {
            alertButton.accept();
        } else {
            alertButton.dismiss();
        }
    }

}
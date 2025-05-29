package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeliveryDashboardPage extends AbstractAdminPage{
    public DeliveryDashboardPage(WebDriver driver) {
        super(driver, new WebDriverWait(driver, Duration.ofSeconds(3)));
    }

    // Troca entre as etapas do fluxo de entrega/devolução
    public void navigateDeliveryPipeline(String pipelineStepName) {
        WebElement link;
        String currentUrl = driver.getCurrentUrl();
        String elementId = getElementId(pipelineStepName);
        if(elementId == null) {
            throw new IllegalArgumentException("Opção inválida: " + pipelineStepName);
        }
        link = driver.findElement(By.id(elementId));
        link.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    private static String getElementId(String pipelineStepName) {
        Map<String, String> stepToId = Map.of(
                "inProcessing", "link-in-processing",
                "inTransit", "link-in-transit",
                "delivered", "link-delivered",
                "returning", "link-returning",
                "returned", "link-returned",
                "returnFinished", "link-return-finished",
                "cancelDeliver", "link-cancel-deliver",
                "cancelRefund", "link-cancel-refund"
        );

        return stepToId.get(pipelineStepName);
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
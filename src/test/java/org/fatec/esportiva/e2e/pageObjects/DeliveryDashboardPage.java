package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        By statusLocator;
        WebElement button;

        statusLocator = By.id("status");
        wait.until(ExpectedConditions.visibilityOfElementLocated(statusLocator));
        String statusText = driver.findElement(statusLocator).getText();
        String elementId = getElementId(pipelineStepName);
        if(elementId == null) {
            throw new IllegalArgumentException("Opção inválida: " + pipelineStepName);
        }
        button = wait.until(ExpectedConditions.elementToBeClickable(By.id(elementId)));
        button.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(statusLocator, statusText)));
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
        By buttonLocator = By.id((approve ? "approve-" : "reprove-") + id);

        button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));
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
        By buttonLocator;
        String approveId;

        HashMap<String, String> idByApprove = new HashMap<>();
        idByApprove.put("approveStock", "approve-stock-");
        idByApprove.put("approve", "approve-");
        idByApprove.put("reprove", "reprove-");

        approveId = idByApprove.get(approve);

        if(approveId == null){
            throw new IllegalArgumentException("Opção inválida: " + approve);
        }
        buttonLocator = By.id(approveId + id);

        button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));
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
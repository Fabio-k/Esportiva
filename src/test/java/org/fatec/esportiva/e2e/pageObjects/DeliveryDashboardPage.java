package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeliveryDashboardPage extends AbstractAdminPage {
    public DeliveryDashboardPage(WebDriver driver) {
        super(driver, new WebDriverWait(driver, Duration.ofSeconds(3)));
    }

    // Troca entre as etapas do fluxo de entrega/devolução
    public void navigateDeliveryPipeline(String pipelineStepName) {
        WebElement button;

        String groupId = getGroupByName(pipelineStepName);
        if (groupId == null) {
            throw new IllegalArgumentException("Opção inválida: " + pipelineStepName);
        }
        String elementId = getStatusByName(pipelineStepName);
        if (elementId == null) {
            throw new IllegalArgumentException("Opção inválida: " + pipelineStepName);
        }

        button = wait.until(ExpectedConditions.elementToBeClickable(By.id(groupId)));
        button.click();
        button = wait.until(ExpectedConditions.elementToBeClickable(By.id(elementId)));
        button.click();
    }

    // Obtém os links dos menus
    // Converte os IDs do antigo formulário para o novo
    private static String getGroupByName(String pipelineStepName) {
        Map<String, String> stepToId = Map.of(
                "inProcessing", "deliver-pipeline",
                "inTransit", "deliver-pipeline",
                "delivered", "deliver-pipeline",
                "returning", "returning-pipeline",
                "returned", "returning-pipeline",
                "returnFinished", "returning-pipeline",
                "cancelDeliver", "deliver-pipeline",
                "cancelRefund", "returning-pipeline");

        return stepToId.get(pipelineStepName);
    }

    

    // Converte os IDs do antigo formulário para o novo
    private static String getStatusByName(String pipelineStepName) {
        Map<String, String> stepToId = Map.of(
                "inProcessing", "link-em_processamento",
                "inTransit", "link-em_transito",
                "delivered", "link-entregue",
                "returning", "link-em_troca",
                "returned", "link-trocado",
                "returnFinished", "link-troca_finalizada",
                "cancelDeliver", "link-compra_cancelada",
                "cancelRefund", "link-troca_recusada");

        return stepToId.get(pipelineStepName);
    }

    // Transactions (Compra como um todo)
    public String getTransactionDate(int id) {
        WebElement date = driver.findElement(By.id("purchaseDate-" + id));
        return date.getText();
    }

    public String getTransactionClient(int id) {
        // Tempo extra para carregar a página completamente
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }
        WebElement client = driver.findElement(By.id("client-" + id));
        return client.getText();
    }

    public void transactionApprove(int id, Boolean approve, Boolean isReturnStock) {
        // Escolhe se vai aprovar ou reprovar
        By buttonLocator = By.id((approve ? "approve-" : "reprove-") + id);

        wait.until(ExpectedConditions.elementToBeClickable(buttonLocator)).click();

        if(isReturnStock){
            By checkboxLocator = By.id("isReturnToStock");
            wait.until(ExpectedConditions.elementToBeClickable(checkboxLocator)).click();
        }

        By modalButtonLocator = By.id("modal" + (approve ? "Approve-" : "Reprove-") + id);
        wait.until(ExpectedConditions.elementToBeClickable(modalButtonLocator)).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("item-" + id)));
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
        // Tempo extra para carregar a página completamente
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }
        WebElement client = driver.findElement(By.id("client-" + id));
        return client.getText();
    }

    public void changePipelineTo(String pipe) {
        By buttonLocator;
        if(Objects.equals(pipe, "order")){
            buttonLocator = By.id("returning-pipeline");
        } else {
            buttonLocator = By.id("delivery-pipeline");
        }
        wait.until(ExpectedConditions.elementToBeClickable(buttonLocator)).click();
    }
}
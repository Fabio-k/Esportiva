package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ProductDashboardPage(WebDriver driver) {
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

    public String getProductName(int id) {
        WebElement date = driver.findElement(By.id("name-" + id));
        return date.getText();
    }

    public String getEntryDate(int id) {
        WebElement date = driver.findElement(By.id("entryDate-" + id));
        return date.getText();
    }

    public String getStockQuantity(int id) {
        WebElement date = driver.findElement(By.id("stockQuantity-" + id));
        return date.getText();
    }

    public String getBlockedQuantity(int id) {
        WebElement date = driver.findElement(By.id("blockedQuantity-" + id));
        return date.getText();
    }

    public String getProfitMargin(int id) {
        WebElement date = driver.findElement(By.id("profitMargin-" + id));
        return date.getText();
    }

    public String getCostValue(int id) {
        WebElement date = driver.findElement(By.id("costValue-" + id));
        return date.getText();
    }

    public String getInactivationCategory(int id) {
        WebElement date = driver.findElement(By.id("inactivationCategory-" + id));
        return date.getText();
    }

    public Integer getProductsCount() {
        try {
            WebElement tableBody = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("tbody")));
            return tableBody.findElements(By.tagName("tr")).size();
        } catch (TimeoutException e) {
            return null;
        }
    }
}
package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDashboardPage extends AbstractAdminPage{

    public ProductDashboardPage(WebDriver driver) {
        super(driver, new WebDriverWait(driver, Duration.ofSeconds(3)));
    }

    public String getProductName(int id) {
        WebElement productNameElement = driver.findElement(By.id("name-" + id));
        return productNameElement.getText();
    }

    public String getEntryDate(int id) {
        WebElement entryDateElement = driver.findElement(By.id("entryDate-" + id));
        return entryDateElement.getText();
    }

    public void selectProduct(int id){
        WebElement text = driver.findElement(By.id("name-" + id));
        WebElement panelElement = driver.findElement(By.id("productId"));
        String oldText = panelElement.getText();
        if(panelElement.getText().equals(String.valueOf(id))) return;
        text.click();
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElement(panelElement, oldText)
        ));
    }

    public int getStockQuantity() {
        WebElement stockElement = driver.findElement(By.id("productStock"));
        return Integer.parseInt(stockElement.getText());
    }

    public int getBlockedQuantity() {
        WebElement blockedQuantityElement = driver.findElement(By.id("productBlocked"));
        return Integer.parseInt(blockedQuantityElement.getText());
    }

    public String getProfitMargin(int id) {
        WebElement profitMarginElement = driver.findElement(By.id("profitMargin-" + id));
        return profitMarginElement.getText();
    }

    public String getCostValue(int id) {
        WebElement costValueElement = driver.findElement(By.id("costValue-" + id));
        return costValueElement.getText();
    }

    public String getInactivationCategory(int id) {
        WebElement inactivationCategoryElement = driver.findElement(By.id("inactivationCategory-" + id));
        return inactivationCategoryElement.getText();
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
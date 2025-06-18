package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogPage extends AbstractAdminPage {
    public LogPage(WebDriver driver) {
        super(driver, new WebDriverWait(driver, Duration.ofSeconds(3)));
    }


    public String getId(int id){
        WebElement info = driver.findElement(By.id("log-" + id));
        return info.getText();
    }

    public String getUser(int id){
        WebElement info = driver.findElement(By.id("user-" + id));
        return info.getText();
    }

    public String getTimestamp(int id){
        WebElement info = driver.findElement(By.id("timestamp-" + id));
        return info.getText();
    }

    public String getOperation(int id){
        WebElement info = driver.findElement(By.id("operation-" + id));
        return info.getText();
    }

    public String getOperationContent(int id){
        WebElement info = driver.findElement(By.id("operationContent-" + id));
        return info.getText();
    }
}

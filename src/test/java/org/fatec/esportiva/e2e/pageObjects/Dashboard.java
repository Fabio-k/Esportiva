package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Dashboard {
    private WebDriver driver;
    private WebDriverWait wait;

    public Dashboard(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void setUserName(int id) {
        driver.findElement(By.id("name-" + id));
    }

    public String getUserName(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name-" + id)));
        return content.getText();
    }

}

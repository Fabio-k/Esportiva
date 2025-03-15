package org.fatec.esportiva.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class E2E {
    protected static WebDriver browser;
    protected static WebDriverWait wait;

    protected final String baseUrl = "http://localhost:8080";

    static {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeAll
    static void setup() {
        browser = new ChromeDriver();
        wait = new WebDriverWait(browser, Duration.ofSeconds(15));
    }

    @AfterAll
    static void teardown() {
        browser.quit();
    }

    public void authenticateAs(String name) {
        wait.until(ExpectedConditions.urlContains("/login"));
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submitButton")));
        selectByText(By.id("users"), name);

        submitButton.click();

        wait.until(ExpectedConditions.stalenessOf(submitButton));
    }

    public WebElement waitUntilId(String id) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        sleep();
        return element;
    }

    public void fillInputField(By locator, String value) {
        WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        inputField.clear();
        inputField.sendKeys(value);
        sleep();
    }

    public void selectByText(By locator, String visibleText) {
        WebElement selectField = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(selectField);
        select.selectByVisibleText(visibleText);
        sleep();
    }

    public void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package org.fatec.esportiva.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

    @BeforeEach
    void setup() {
        browser = new ChromeDriver(new ChromeOptions().addArguments("--headless"));
        wait = new WebDriverWait(browser, Duration.ofSeconds(15));
    }

    @AfterEach
    void teardown() {
        browser.quit();
    }

    public void fillInputField(By locator, String value) {
        WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        inputField.clear();
        inputField.sendKeys(value);
    }

    public void selectByText(By locator, String visibleText) {
        WebElement selectField = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(selectField);
        select.selectByVisibleText(visibleText);
    }
}

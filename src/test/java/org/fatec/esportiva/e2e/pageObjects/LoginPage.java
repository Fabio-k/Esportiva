package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void login(String userName) {
        // Encontra as opções e cria um objeto desse tipo
        driver.get("http://localhost:8080/login");
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("users")));
        Select select = new Select(dropdown);

        // Seleciona pelo texto que está visível nas opções
        select.selectByVisibleText(userName);

        // Acha o botão e clica nele
        WebElement submitButton = driver.findElement(By.id("submitButton"));
        submitButton.click();

        // Espera a nova página ser carregada, quando o botão fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(submitButton));
    }

    public Boolean getErrorMessage() {
        try {
            WebElement erro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error")));

            // Verifique a mensagem de erro da seleção de usuários
            if (erro.getText().equals("Escolha um usuário")) {
                return true;
            } else {
                return false;
            }

        }
        // Se o elemento não aparecer, o Selenium dá um Timeout
        catch (TimeoutException e) {
            return false;
        }
    }

}

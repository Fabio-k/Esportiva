package org.fatec.esportiva.e2e.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractAdminPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public AbstractAdminPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Navega entre as páginas do administrador
    public void navigateAdminPages(String adminPageName) {
        WebElement link;

        // Procura o link pelo conteúdo dentro do texto
        List<String> validPages = Arrays.asList(
                "Dashboard", "Estoque", "Entrega", "Análise", "Log", "Logout"
        );

        if(!validPages.contains(adminPageName)) {
            throw new IllegalArgumentException("Opção inválida: " + adminPageName);
        }

        link = driver.findElement(By.linkText(adminPageName));
        link.click();

        // Espera a nova página ser carregada, quando o link fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(link));
    }
}

package org.fatec.esportiva.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;

// Carrega o arquivo de '.properties' seguindo o padrão: application-{profile}.properties
//@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class E2E {
    protected static WebDriver browser;
    protected static WebDriverWait wait;

    protected final String baseUrl = "http://localhost:8080";

    // Executado antes de qualquer coisa e uma única vez
    // Configura o navegador do Chrome e baixa o driver correspondente
    static {
        WebDriverManager.chromedriver().setup();
    }

    @AfterAll
    static void teardown() {
        browser.quit();
    }
}

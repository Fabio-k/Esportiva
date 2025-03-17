package org.fatec.esportiva.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class E2E {
    protected static WebDriver browser;
    protected static WebDriverWait wait;
    private static final int sleepDuration = 0;

    @Autowired
    private Flyway flyway;

    protected final String baseUrl = "http://localhost:8080";

    // Executado antes de qualquer coisa e uma única vez
    // Configura o navegador do Chrome e baixa o driver correspondente
    static {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        flyway.clean();
        flyway.migrate();
    }

    @AfterAll
    static void teardown() {
        browser.quit();
    }

    protected void sleepForVisualization() {
        try {
            Thread.sleep(sleepDuration);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }
    }
}

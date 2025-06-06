package org.fatec.esportiva.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
// Problema com a propriedade é que os testes não são totalmente isolados entre si
// Uma vez carregado o Spring Boot, eles compartilham as mesmas propriedades
// https://stackoverflow.com/questions/48570766/override-a-property-for-a-single-spring-boot-test
// @TestPropertySource(properties = "cart.product.timeoutInMinutes=1")
// @TestPropertySource(properties = "cart.cleanup.intervalInMillis=1000")
public class E2E {
    protected WebDriver browser;
    protected static WebDriverWait wait;
    protected static ChromeOptions options;
    private static final int sleepDuration = 0; // Variar aqui quando for mostrar para alguém

    protected final String baseUrl = "http://localhost:8080";

    // Executado antes de qualquer coisa e uma única vez
    // Configura o navegador do Chrome e baixa o driver correspondente
    static {
        WebDriverManager.chromedriver().setup();
        // https://www.selenium.dev/documentation/webdriver/drivers/options/
        options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        // options.addArguments("--headless");
    }

    @BeforeEach
    void setup(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
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

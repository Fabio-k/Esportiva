package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;

import org.fatec.esportiva.entity.enums.ResidencyType;
import org.fatec.esportiva.entity.enums.StreetType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutNewAddressPage {
    @SuppressWarnings("unused")
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutNewAddressPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void saveAddressCheckbox(boolean check) {
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("saveAddress")));

        if (check) {
            if (!checkbox.isSelected()) {
                checkbox.click(); // Marca o checkbox
            }
        } else {
            if (checkbox.isSelected()) {
                checkbox.click(); // Desmarca o checkbox
            }
        }
    }

    public void setAddressIdentificationPhrase(String identificationPhrase) {
        WebElement field = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.id("addressIdentificationPhrase")));

        field.clear();
        field.sendKeys(identificationPhrase);
    }

    public void setAddressCep(String Cep) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cep")));

        field.clear();
        field.sendKeys(Cep);
    }

    public void setAddressResidenceType(ResidencyType residencyType) {
        // Encontra as opções e cria um objeto desse tipo
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("residencyType")));
        Select select = new Select(dropdown);

        // Seleciona pelo texto que está visível nas opções
        select.selectByVisibleText(residencyType.getDisplayName());
    }

    public void setAddressStreetType(StreetType streetType) {
        // Encontra as opções e cria um objeto desse tipo
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("streetType")));
        Select select = new Select(dropdown);

        // Seleciona pelo texto que está visível nas opções
        select.selectByVisibleText(streetType.getDisplayName());
    }

    public void setAddressStreet(String street) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("street")));

        field.clear();
        field.sendKeys(street);
    }

    public void setAddressNumber(String number) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("number")));

        field.clear();
        field.sendKeys(number);
    }

    public void setAddressNeighborhood(String neighborhood) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("neighborhood")));

        field.clear();
        field.sendKeys(neighborhood);
    }

    public void setAddressCity(String city) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("city")));

        field.clear();
        field.sendKeys(city);
    }

    public void setAddressState(String state) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("state")));

        field.clear();
        field.sendKeys(state);
    }

    public void setAddressCountry(String country) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("country")));

        field.clear();
        field.sendKeys(country);
    }

    public void setAddressObservation(String observation) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("observation")));

        field.clear();
        field.sendKeys(observation);
    }

    public void continueShopping(boolean expectedPass) {
        WebElement button = driver.findElement(By.className("primaryButton"));
        String currentUrl = driver.getCurrentUrl();

        button.click();

        // Só aguarda a transição de página se espera-se que ele passe
        // Em caso de falhas não precisa aguardar
        if (expectedPass) {
            // Espera a nova página ser carregada, quando a URL atual fica inválida
            wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
            // Tempo extra para visualizar o teste
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                System.out.println("A pausa foi interrompida!");
                e.printStackTrace();
            }
        }
    }
}
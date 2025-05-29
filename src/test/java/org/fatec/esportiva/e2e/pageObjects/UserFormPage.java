package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.fatec.esportiva.entity.enums.CreditCardBrand;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.PhoneType;
import org.fatec.esportiva.entity.address.ResidencyType;
import org.fatec.esportiva.entity.address.StreetType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("unused")
public class UserFormPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By usernameField = By.id("name");
    private final By genderField = By.id("gender");
    private final By emailField = By.id("email");
    private final By cpfField = By.id("cpf");
    private final By dateBirthField = By.id("dateBirth");
    private final By telephoneTypeField = By.id("telephoneType");
    private final By telephoneField = By.id("telephone");

    public UserFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Interações
    public void addAddress() {
        WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("addAddressButton")));
        submitButton.click();
    }

    public void removeAddress() {
        WebElement submitButton = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.id("addressRemoveButton")));
        submitButton.click();
    }

    public void addCreditCard() {
        WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("addCreditCardButton")));
        submitButton.click();
    }

    public void removeCreditCard() {
        WebElement submitButton = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.id("creditCardRemoveButton")));
        submitButton.click();
    }

    public void clickSaveUser() {
        WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("submitButton")));
        submitButton.click();
        wait.until(ExpectedConditions.stalenessOf(submitButton));
    }

    // SETTERS
    // ####################################################################################################
    public void setName(String userName) {
        WebElement field = driver.findElement(usernameField);

        field.clear();
        field.sendKeys(userName);
    }

    public void setGender(Gender gender) {
        // Encontra as opções e cria um objeto desse tipo
        WebElement dropdown = driver.findElement(genderField);
        Select select = new Select(dropdown);

        // Seleciona pelo texto que está visível nas opções
        select.selectByVisibleText(gender.getDisplayName());
    }

    public void setEmail(String email) {
        WebElement field = driver.findElement(emailField);

        field.clear();
        field.sendKeys(email);
    }

    public void setCpf(String cpf) {
        WebElement field = driver.findElement(cpfField);

        field.clear();
        field.sendKeys(cpf);
    }

    public void setDateBirth(String dateBirth) {
        WebElement field = driver.findElement(dateBirthField); 

        field.clear();
        field.sendKeys(dateBirth);
    }

    public void setTelephoneType(PhoneType telephoneType) {
        // Encontra as opções e cria um objeto desse tipo
        WebElement dropdown = driver.findElement(telephoneTypeField); 
        Select select = new Select(dropdown);

        // Seleciona pelo texto que está visível nas opções
        select.selectByVisibleText(telephoneType.getDisplayName());
    }

    public void setTelephone(String telephone) {
        WebElement field = driver.findElement(telephoneField);

        field.clear();
        field.sendKeys(telephone);
    }

    // Endereço
    public void setAddressCategoryResidential(int id, boolean check) {
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("residence_" + id)));

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

    public void setAddressCategoryBilling(int id, boolean check) {
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("billing_" + id)));

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

    public void setAddressCategoryShipping(int id, boolean check) {
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("shipping_" + id)));

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

    public void setAddressIdentificationPhrase(int id, String identificationPhrase) {
        WebElement field = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.id("addressIdentificationPhrase_" + id)));

        field.clear();
        field.sendKeys(identificationPhrase);
    }

    public void setAddressCep(int id, String Cep) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cep_" + id)));
        WebElement city = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("city_" + id)));

        field.clear();
        field.sendKeys(Cep);
        wait.until(driver -> !city.getAttribute("value").isEmpty());
    }

    public void setAddressResidenceType(int id, ResidencyType residencyType) {
        // Encontra as opções e cria um objeto desse tipo
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("residencyType_" + id)));
        Select select = new Select(dropdown);

        // Seleciona pelo texto que está visível nas opções
        select.selectByVisibleText(residencyType.getDisplayName());
    }

    public void setAddressStreetType(int id, StreetType streetType) {
        // Encontra as opções e cria um objeto desse tipo
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("streetType_" + id)));
        Select select = new Select(dropdown);

        // Seleciona pelo texto que está visível nas opções
        select.selectByVisibleText(streetType.getDisplayName());
    }

    public void setAddressStreet(int id, String street) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("street_" + id)));

        field.clear();
        field.sendKeys(street);
    }

    public void setAddressNumber(int id, String number) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("number_" + id)));

        field.clear();
        field.sendKeys(number);
    }

    public void setAddressNeighborhood(int id, String neighborhood) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("neighborhood_" + id)));

        field.clear();
        field.sendKeys(neighborhood);
    }

    public void setAddressCity(int id, String city) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("city_" + id)));

        field.clear();
        field.sendKeys(city);
    }

    public void setAddressState(int id, String state) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("state_" + id)));

        field.clear();
        field.sendKeys(state);
    }

    public void setAddressCountry(int id, String country) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("country_" + id)));

        field.clear();
        field.sendKeys(country);
    }

    public void setAddressObservation(int id, String observation) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("observation_" + id)));

        field.clear();
        field.sendKeys(observation);
    }

    // Cartão de crédito
    public void setCreditCardName(int id, String cardName) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("creditCardName_" + id)));

        field.clear();
        field.sendKeys(cardName);
    }

    public void setCreditCardNumber(int id, String cardNumber) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("creditCardNumber_" + id)));

        field.clear();
        field.sendKeys(cardNumber);
    }

    public void setCreditCardCVV(int id, String CVV) {
        WebElement field = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.id("creditCardSecurityCode_" + id)));

        field.clear();
        field.sendKeys(CVV);
    }

    public void setCreditCardBrand(int id, CreditCardBrand brand) {
        // Encontra as opções e cria um objeto desse tipo
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("creditCardBrand_" + id)));
        Select select = new Select(dropdown);

        // Seleciona pelo texto que está visível nas opções
        select.selectByValue(brand.name());
    }

    // GETTERS
    // ####################################################################################################
    public String getName() {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
        return content.getAttribute("value");
    }

    public String getGender() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("gender")));
        Select select = new Select(dropdown);

        // Obtenha o texto da opção selecionada
        return select.getFirstSelectedOption().getText();
    }

    public String getEmail() {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        return content.getAttribute("value");
    }

    public String getCpf() {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cpf")));
        return content.getAttribute("value");
    }

    public String getDateBirth() {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dateBirth")));
        return content.getAttribute("value");
    }

    public String getTelephoneType() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("telephoneType")));
        Select select = new Select(dropdown);

        // Obtenha o texto da opção selecionada
        return select.getFirstSelectedOption().getText();
    }

    public String getTelephone() {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("telephone")));
        return content.getAttribute("value");
    }

    // Endereço
    public Boolean getAddressCategoryResidential(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("residence_" + id)));
        return content.isSelected();
    }

    public Boolean getAddressCategoryBilling(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("billing_" + id)));
        return content.isSelected();
    }

    public Boolean getAddressCategoryShipping(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("shipping_" + id)));
        return content.isSelected();
    }

    public String getAddressIdentificationPhrase(int id) {
        WebElement content = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.id("addressIdentificationPhrase_" + id)));
        return content.getAttribute("value");
    }

    public String getAddressCep(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cep_" + id)));
        return content.getAttribute("value");
    }

    public String getAddressResidenceType(int id) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("residencyType_" + id)));
        Select select = new Select(dropdown);

        // Obtenha o texto da opção selecionada
        return select.getFirstSelectedOption().getText();
    }

    public String getAddressStreetType(int id) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("streetType_" + id)));
        Select select = new Select(dropdown);

        // Obtenha o texto da opção selecionada
        return select.getFirstSelectedOption().getText();

    }

    public String getAddressStreet(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("street_" + id)));
        return content.getAttribute("value");
    }

    public String getAddressNumber(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("number_" + id)));
        return content.getAttribute("value");
    }

    public String getAddressNeighborhood(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("neighborhood_" + id)));
        return content.getAttribute("value");
    }

    public String getAddressCity(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("city_" + id)));
        return content.getAttribute("value");
    }

    public String getAddressState(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("state_" + id)));
        return content.getAttribute("value");
    }

    public String getAddressCountry(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("country_" + id)));
        return content.getAttribute("value");
    }

    public String getAddressObservation(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("observation_" + id)));
        return content.getAttribute("value");
    }

    // Cartão de crédito
    public String getCreditCardName(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("creditCardName_" + id)));
        return content.getAttribute("value");
    }

    public String getCreditCardNumber(int id) {
        WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("creditCardNumber_" + id)));
        return content.getAttribute("value");
    }

    public String getCreditCardCVV(int id) {
        WebElement content = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.id("creditCardSecurityCode_" + id)));
        return content.getAttribute("value");
    }

    public String getCreditCardBrand(int id) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("creditCardBrand_" + id)));
        Select select = new Select(dropdown);

        // Obtenha o texto da opção selecionada
        return select.getFirstSelectedOption().getText();
    }

    public boolean isErrorMessagePresent(String expectedMessage) {
        // Localiza todos os elementos com a classe "error"
        List<WebElement> errorMessages = wait
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("error")));
        List<String> debbuging = new ArrayList<>();

        // Itera por todos os elementos e verifica se o texto corresponde
        for (WebElement error : errorMessages) {
            debbuging.add(error.getText());
            if (error.getText().contains(expectedMessage)) {
                return true;
            }
        }
        return false;
    }
}

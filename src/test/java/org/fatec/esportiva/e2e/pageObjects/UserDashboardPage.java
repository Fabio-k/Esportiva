package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;

import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserDashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public UserDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    // Navega entre as páginas do administrador
    public void navigateAdminPages(String adminPageName) {
        WebElement link;

        // Procura o link pelo conteúdo dentro do texto
        if (adminPageName == "Dashboard") {
            link = driver.findElement(By.linkText("Dashboard"));

        } else if (adminPageName == "Estoque") {
            link = driver.findElement(By.linkText("Estoque"));

        } else if (adminPageName == "Entrega") {
            link = driver.findElement(By.linkText("Entrega"));

        } else if (adminPageName == "Análise") {
            link = driver.findElement(By.linkText("Análise"));

        } else if (adminPageName == "Log") {
            link = driver.findElement(By.linkText("Log"));

        } else if (adminPageName == "Logout") {
            link = driver.findElement(By.linkText("Logout"));

        } else {
            throw new IllegalArgumentException("Opção inválida: " + adminPageName);
        }

        link.click();

        // Espera a nova página ser carregada, quando o link fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(link));
    }

    public void clickNewUser() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable((By.id("createClientButton"))));
        button.click();

        wait.until(ExpectedConditions.stalenessOf(button));
    }

    public void editUser(int id) {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable((By.id("edit-" + id))));
        button.click();

        wait.until(ExpectedConditions.stalenessOf(button));
    }

    public void deleteUser(int id, Boolean confirmDelete) {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable((By.id("delete-" + id))));
        button.click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alertButton = driver.switchTo().alert();
        if (confirmDelete) {
            alertButton.accept();
            // Aguarda a linha ser deletada
            wait.until(ExpectedConditions.stalenessOf(button));
        } else {
            alertButton.dismiss();
        }
    }

    public void ApplyFilter() {
        WebElement filterButton = driver.findElement(By.id("filter-submit"));
        filterButton.click();
    }

    public void openFilter() {
        WebElement openFilter = driver.findElement(By.id("filterButton"));
        openFilter.click();
    }

    public void setFilterName(String userName) {
        WebElement field = driver.findElement(By.id("filter-name"));
        field.sendKeys(userName);
    }

    public void setFilterCpf(String cpf) {
        WebElement field = driver.findElement(By.id("filter-name"));
        field.sendKeys(cpf);
    }

    public void setFilterGender(Gender gender) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("filter-gender")));
        Select select = new Select(dropdown);

        select.selectByVisibleText(gender.getDisplayName());
    }

    public void setFilterEmail(String email) {
        WebElement field = driver.findElement(By.id("filter-email"));
        field.sendKeys(email);
    }

    public void setFilterStatus(UserStatus userStatus) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("filter-status")));
        Select select = new Select(dropdown);

        select.selectByVisibleText(userStatus.getDisplayName());
    }

    public String getUserId(int id) {
        try {
            WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id-" + id)));
            return content.getText();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public String getUserName(int id) {
        try {
            WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name-" + id)));
            return content.getText();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public String getUserCpf(int id) {
        try {
            WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cpf-" + id)));
            return content.getText();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public String getUserDateBirth(int id) {
        try {
            WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dateBirth-" + id)));
            return content.getText();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public String getUserGender(int id) {
        try {
            WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("gender-" + id)));
            return content.getText();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public String getUserTelephone(int id) {
        try {
            WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("telephone-" + id)));
            return content.getText();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public String getUserEmail(int id) {
        try {
            WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email-" + id)));
            return content.getText();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public String getUserStatus(int id) {
        try {
            WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("status-" + id)));
            return content.getText();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public Integer getUsersCount() {
        try {
            WebElement tableBody = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("tbody")));
            return tableBody.findElements(By.tagName("tr")).size();
        } catch (TimeoutException e) {
            return null;
        }
    }

}

package org.fatec.esportiva.e2e.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AnalysisPage {
    private WebDriver driver;
    private JavascriptExecutor javascriptExecutor;

    public AnalysisPage(WebDriver driver) {
        this.driver = driver;
        this.javascriptExecutor = (JavascriptExecutor) driver;
    }

    public void setProduct(String productName) {
        // Encontra as opções e cria um objeto desse tipo
        WebElement dropdown = driver.findElement(By.id("historySelect"));
        Select select = new Select(dropdown);

        // Seleciona pelo texto que está visível nas opções
        select.selectByVisibleText(productName);
    }

    public void setStartDate(String date){
        WebElement startDateInput = driver.findElement(By.id("startDate"));
        startDateInput.sendKeys(date); // Inserir a data no formato correto (ddmmyyyy)
        
        // Como digitar altera a exibição. Isso força a atualizar os dados após digitar a data por completo
        startDateInput.sendKeys(Keys.ENTER);
        // Tempo extra para carregar o gráfico
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }
    }

    public void setEndDate(String date){
        WebElement endDateInput = driver.findElement(By.id("endDate"));
        endDateInput.sendKeys(date); // Inserir a data no formato correto (ddmmyyyy)
        
        // Como digitar altera a exibição. Isso força a atualizar os dados após digitar a data por completo
        endDateInput.sendKeys(Keys.ENTER);
        // Tempo extra para carregar o gráfico
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }
    }
    
    public void deleteTrace(String productName) throws Exception{
        WebElement divButtonTraces = driver.findElement(By.id("traces"));
        
        // Encontrar todos os botões dentro do Div
        List<WebElement> buttons = divButtonTraces.findElements(By.tagName("button"));

        // Iterar sobre os botões para encontrar o botão desejado pelo nome do produto
        for (WebElement button : buttons) {
            WebElement paragraph = button.findElement(By.tagName("p"));

            // Se achar o botão, clica nele para apagar o produto
            if (paragraph.getText() == productName){
                button.click();
                return; // Encerra o método, porque já executou a ação desejada
            }
        }

        // Não existe o botão que está tentando apagar
        throw new Exception("Não foi possível encontrar o botão '" + productName + "'");
    }


    public Long getBarValue(int index){
        // Tempo extra para carregar o gráfico
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }
        @SuppressWarnings("unchecked")
        List<Long> barValue = (List<Long>) javascriptExecutor.executeScript("return barValues;");
        return barValue.get(index);
    }
}

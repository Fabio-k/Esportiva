package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClientHistoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ClientHistoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void logout() {
        WebElement link = driver.findElement(By.linkText("Sair"));
        link.click();
        // Espera a nova página ser carregada, quando o link fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(link));
    }

    public void mainPage() {
        WebElement link = driver.findElement(By.cssSelector("a[href='/']"));
        link.click();
        // Espera a nova página ser carregada, quando o link fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(link));
    }

    public void requestsReturnAllItens(int cardPosition) {
        WebElement transaction = getCardTransaction(cardPosition);
        WebElement button = transaction
                .findElement(By.xpath("//button[text()='Solicitar troca de todos os itens do pedido']"));

        button.click();
    }

    public String getTransactionDate(int cardPosition) {
        WebElement transaction = getCardTransaction(cardPosition);

        // As informações do card estão no primeiro div
        WebElement transactionAttributes = transaction.findElement(By.cssSelector("> div"));

        // A data fica dentro do h3
        WebElement date = transactionAttributes.findElement(By.tagName("h3"));
        return date.getText();
    }

    public String getTransactionStatus(int cardPosition) {
        WebElement transaction = getCardTransaction(cardPosition);
        WebElement status = transaction.findElement(By.cssSelector("p.inactiveText.activeText"));
        return status.getText();
    }

    public String getItemName(int cardPosition, int itemPosition) {
        WebElement transaction = getCardTransaction(cardPosition);
        WebElement item = getItem(transaction, itemPosition);

        WebElement firstDiv = item.findElement(By.xpath("//div[position()=1]"));
        WebElement itemAttributes = firstDiv.findElement(By.xpath("//div[position()=2]"));

        WebElement name = itemAttributes.findElement(By.xpath("//p[position()=1]"));
        return name.getText();

    }

    public int getItemQuantity(int cardPosition, int itemPosition) {
        WebElement transaction = getCardTransaction(cardPosition);
        WebElement item = getItem(transaction, itemPosition);

        WebElement firstDiv = item.findElement(By.xpath("//div[position()=1]"));
        WebElement itemAttributes = firstDiv.findElement(By.xpath("//div[position()=2]"));

        WebElement quantity = itemAttributes.findElement(By.xpath("//p[position()=2]"));
        return Integer.parseInt(quantity.getText().replace("Quantidade: ", ""));
    }

    public String getItemReturnStatus(int cardPosition, int itemPosition) {
        WebElement transaction = getCardTransaction(cardPosition);
        WebElement item = getItem(transaction, itemPosition);

        WebElement firstDiv = item.findElement(By.xpath("//div[position()=1]"));
        WebElement statusReturn = firstDiv.findElement(By.cssSelector(" > p"));
        return statusReturn.getText();
    }

    public void itemRequestReturn(int cardPosition, int itemPosition) {
        WebElement transaction = getCardTransaction(cardPosition);
        WebElement item = getItem(transaction, itemPosition);
        WebElement button = item.findElement(By.cssSelector("a.primaryButton"));

        button.click();
    }

    private WebElement getCardTransaction(int cardPosition) {
        // Encontra todos os itens e retorna somente um da posição desejada
        List<WebElement> allTransactions = driver.findElements(By.cssSelector(".card transaction"));
        return allTransactions.get(cardPosition);
    }

    private WebElement getItem(WebElement cardTransaction, int itemPosition) {
        // Seleciona todos os filhos <div>, menos o primeiro
        // * O primeiro é o elemento relacionado ao título
        List<WebElement> allItens = cardTransaction.findElements(By.cssSelector("> div:not(:first-child)"));
        return allItens.get(itemPosition);
    }
}
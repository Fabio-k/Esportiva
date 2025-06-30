package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

    public void linkPurchasedItems() {
        WebElement link = driver.findElement(By.cssSelector("a[href='/transactions']"));
        link.click();
        // Espera a nova página ser carregada, quando o link fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(link));
    }

    public void linkReturnedItems() {
        WebElement link = driver.findElement(By.cssSelector("a[href='/transactions/trade']"));
        link.click();
        // Espera a nova página ser carregada, quando o link fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(link));
    }

    public String getTransactionDate(int cardPosition) {
        WebElement transaction = getCardTransaction(cardPosition);

        // As informações do card estão no primeiro div
        WebElement transactionAttributes = transaction.findElement(By.tagName("div"));

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

        WebElement firstDiv = item.findElement(By.tagName("div"));
        WebElement itemAttributes = firstDiv.findElements(By.xpath("./div")).get(1);

        WebElement name = itemAttributes.findElements(By.tagName("p")).get(0);
        String itemName = name.getText();
        return itemName;
    }

    public int getItemQuantity(int cardPosition, int itemPosition) {
        WebElement transaction = getCardTransaction(cardPosition);
        WebElement item = getItem(transaction, itemPosition);

        WebElement firstDiv = item.findElement(By.tagName("div"));
        WebElement itemAttributes = firstDiv.findElements(By.xpath("./div")).get(1);

        WebElement quantity = itemAttributes.findElements(By.tagName("p")).get(1);
        return Integer.parseInt(quantity.getText().replace("Quantidade: ", ""));
    }

    public String getItemReturnStatus(int cardPosition, int itemPosition) {
        WebElement transaction = getCardTransaction(cardPosition);
        WebElement item = getItem(transaction, itemPosition);

        WebElement firstDiv = item.findElement(By.tagName("div"));
        WebElement statusReturn = firstDiv.findElement(By.cssSelector(" > p"));
        return statusReturn.getText();
    }

    private WebElement getCardTransaction(int cardPosition) {
        // Encontra todos os itens e retorna somente um da posição desejada
        List<WebElement> allTransactions = driver.findElements(By.cssSelector(".card.transaction"));
        return allTransactions.get(cardPosition);
    }

    private WebElement getItem(WebElement cardTransaction, int itemPosition) {
        // Seleciona todos os filhos <div>, menos o primeiro
        // * O primeiro é o elemento relacionado ao título
        List<WebElement> allItens = cardTransaction.findElements(By.xpath("./div"));
        itemPosition++; // O primeiro 'div' contém as informações da transação, não o item
        return allItens.get(itemPosition);
    }

    public void requestsReturnAllItens(int cardPosition) {
        // Já pede a devolução de todos os itens em todas as quantidades, então não tem
        // tela nova
        WebElement transaction = getCardTransaction(cardPosition);
        WebElement button = transaction
                .findElement(By.xpath("//button[text()='Solicitar troca de todos os itens do pedido']"));

        button.click();
    }

    public void itemRequestReturn(int cardPosition, int itemPosition, int quantity, boolean confirm) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("A quantidade não pode ser zero ou negativa");
        }

        // Tempo extra para carregar a página completamente
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }
        // Seleciona o produto para trocar (Muda de tela)
        WebElement transaction = getCardTransaction(cardPosition);
        WebElement item = getItem(transaction, itemPosition);
        WebElement button = item.findElement(By.cssSelector("a.primaryButton"));

        button.click();
        // Espera a nova página ser carregada, quando o botão fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(button));
        // Tempo extra para carregar a página completamente
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }

        // Seleciona a quantidade de itens e confirma
        WebElement dropdown = driver.findElement(By.id("tradeQuantity"));
        // Criar um objeto Select e selecionar opções
        Select selectQuantity = new Select(dropdown);
        selectQuantity.selectByValue(String.valueOf(quantity)); // Seleciona a opção pelo atributo 'value'

        if (confirm) {
            WebElement buttonConfirmReturn = driver.findElement(By.className("primaryButton"));
            buttonConfirmReturn.click();
        } else {
            WebElement buttonCancelReturn = driver.findElement(By.className("secondaryButton"));
            buttonCancelReturn.click();
        }
        // Espera a nova página ser carregada, quando o select fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(dropdown));
    }
}
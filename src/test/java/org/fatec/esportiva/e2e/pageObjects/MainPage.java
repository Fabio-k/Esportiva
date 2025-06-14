package org.fatec.esportiva.e2e.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void logout() {
        WebElement link = driver.findElement(By.linkText("Sair"));
        link.click();
        // Espera a nova página ser carregada, quando o link fica 'inválido'
        wait.until(ExpectedConditions.stalenessOf(link));
    }

    public void searchProduct(String search) {
        WebElement searchBar = driver.findElement(By.id("searchInput"));

        searchBar.clear();
        searchBar.sendKeys(search + "\n"); // Pressiona ENTER para executar a busca

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products-container")));
    }

    public String getNotificationMessage(int position) {
        // Clica no ícone para conseguir pegar o texto das notificações
        WebElement notificationIcon = driver.findElement(By.id("notificationIcon"));
        notificationIcon.click();

        WebElement notificationContainer = driver.findElement(By.id("notificationList"));

        // Encontra todos os itens e retorna somente um da posição desejada
        List<WebElement> notificationList = notificationContainer.findElements(By.cssSelector(".notificationCard"));
        WebElement notification = notificationList.get(position);
        // O primeiro parágrafo tem a mensagem
        WebElement notificatonMessage = notification.findElement(By.tagName("p"));
        String message = notificatonMessage.getText();

        return message;
    }

    public void selectProduct(int productId) {
        WebElement link = driver.findElement(By.cssSelector("a[href='/products/" + productId + "']"));
        String currentUrl = driver.getCurrentUrl();
        link.click();
        // Espera a nova página ser carregada, quando o link fica 'inválido'
        //wait.until(ExpectedConditions.stalenessOf(link));
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    public void linkClientHistory() {
        WebElement link = driver.findElement(By.linkText("Pedidos"));
        String currentUrl = driver.getCurrentUrl();

        link.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));
    }

    public void linkCart() {
        WebElement link = driver.findElement(By.id("link-cart"));
        String currentUrl = driver.getCurrentUrl();

        link.click();

        // Espera a nova página ser carregada, quando a URL atual fica inválida
        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(currentUrl));

    }

    public void openChatbot(){
        WebElement button = driver.findElement(By.id("chatButton"));
        button.click();

        // Espera abrir o chat
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chatModal")));
    }

    public void chatbotSendMessage(String message){
        WebElement chatbotInput = driver.findElement(By.id("message"));
        chatbotInput.sendKeys(message);
        chatbotInput.sendKeys(Keys.ENTER);

        // Espera a IA responder
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("A pausa foi interrompida!");
            e.printStackTrace();
        }
    }

    public String chatbotGetMessage() throws Exception{
        WebElement allChat = driver.findElement(By.id("messagesDiv")); 
        
        // Número de tentativas que vai esperar a IA responder
        int numberAttempts = 10;
        int delayByAttempt = 500;
        for (int i = 0; i < numberAttempts; i++) {
            // Encontra cada uma das mensagens do chat
            List<WebElement> messages = allChat.findElements(By.tagName("span"));
            WebElement lastMessage = messages.get(messages.size() - 1);

            // Para fazer o pooling, e detectar se a IA já respondeu, detecta isso usando o atributo 'aiMessage'
            // Se a última mensagem tiver essa classe, então a IA já respondeu o usuário
            String classProperty = lastMessage.getAttribute("class");
            if (classProperty != null && classProperty.contains("aiMessage")) {
                return lastMessage.getText();
            }

            // Tempo de espera por tentativa
            try {
                Thread.sleep(delayByAttempt);
            } catch (InterruptedException e) {
                System.out.println("A pausa foi interrompida!");
                e.printStackTrace();
            }
        }

        throw new Exception("A IA demorou muito para responder!");
    }
}
package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.response.ProductResponseDto;
import org.fatec.esportiva.exception.AiResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {
    @Value("${ai.api.key}")
    private String apiKey;

    private final String modelName = "gemini-2.0-flash";
    private final ProductService productService;
    private final ClientService clientService;
    private final RestClient restClient;

    public String getRecommendationAnswer(List<String> chatHistory) {
        String prompt = getRecommendationPrompt(chatHistory);
        String requestBody = getRequestBody(prompt);

        ResponseEntity<Map<String, Object>> responseEntity = restClient.post() // ResponseEntity<Map> pois a resposta é
                // JSON
                .uri(uriBuilder -> uriBuilder.path(modelName + ":generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .headers(h -> h.setContentType(MediaType.APPLICATION_JSON))
                .body(requestBody)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String, Object>>() {
                });

        return processAnswer(responseEntity);
    }

    private String getRecommendationPrompt(List<String> chatHistory) {
        String userText = "Recomende produtos com base...\n";
        userText += getAvailableProducts();
        userText += getClientHistory();

        if (chatHistory.size() == 1) {
            // Não tem histórico ainda, só a conversa
        } else if (chatHistory.size() == 2) {
            // Esse caso acho quase impossível de acontecer, porque a conversa flui assim:
            // Cliente -> IA -> Cliente -> IA ...
            userText += "E histórico da última conversa com AI: " + chatHistory.get(0);
        } else if (chatHistory.size() == 3) {
            userText += "E histórico da última conversa com AI: " + chatHistory.get(0) + "|" + chatHistory.get(1);
        }
        // Premissa: Sempre vai ter no máximo 3 mensagens do histórico
        // (Para não passar do limite dos tokens da API gratuita do Gemini)

        // A última mensagem sempre é a pergunta que o usuário acabou de fazer
        userText += "E na Consulta do usuário: " + chatHistory.getLast();

        return userText;
    }

    private String getAvailableProducts() {
        // Define o título dos produtos disponíveis e como a IA vai interpretar a tabela
        String availableProducts = "Na seguinte lista de produtos (id|Nome|Preço|Descrição):\n";

        // Remove todos os atributos indisponíveis (Pelo Status e quantidade no estoque)
        List<ProductResponseDto> productList = productService.getAllAvailableProducts();

        // Obtém somente os atributos relevantes: id | Nome | Preço | Descrição
        for (ProductResponseDto product : productList) {
            availableProducts += product.id() + "|" + product.name() + "|"
                    + product.getFormattedPrice()
                    + "|"
                    + product.description() + "\n";
        }

        return availableProducts;
    }

    private String getClientHistory() {
        // Define o título da seção do histórico do cliente e como a IA vai interpretar
        // a tabela
        String clientHistory = "No histórico do cliente (Nome|Quantidade comprada):\n";
        Map<String, Integer> productsHistory = clientService.getClientHistory();

        // Converte os valores obtidos para a string
        for (Map.Entry<String, Integer> element : productsHistory.entrySet()) {
            clientHistory += element.getKey() + "|" + element.getValue() + "\n";
        }

        return clientHistory;
    }

    private String getRequestBody(String prompt){
        return String.format("""
                {
                "system_instruction": {
                "parts": [
                    {
                    "text": "Você é um assistente virtual da loja de artigos esportivos Esportiva.
                   Sua função também é recomendar produtos da loja.
                   Ao mencionar o nome do produto responda nesse formato: ** id | nome **.
                   Caso o cliente mencione um produto da loja, oriente sobre o uso correto dele.
                   Caso não ache um produto que atenda ao pedido do cliente, mas existe outro que atenda a sua necessidade, responda da seguinte forma: 'Um produto que atende a sua necessidade é esse: ** nome do produto **'
                   Caso não haja um produto na loja que atenda o cliente, responda da seguinte forma: 'Infelizmente não temos um produto que atenda ao seu pedido.'
                   Somente em último caso, caso as respostas anteriores não possam ser usadas, responda da seguinte forma:
                   'Desculpe, só posso ajudar com dúvidas sobre os produtos à venda na loja Esportiva.'"
                    }
                ]
                },
                  "contents": [{
                    "parts": [{"text": "%s"}]
                  }]
                }
                """, prompt);
    }

    private String processAnswer(ResponseEntity<Map<String, Object>> responseEntity) {
        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null)
            throw new AiResponseException("Falha ao chamar a API do Gemini. Status: " + responseEntity.getStatusCode());

        Map<String, Object> responseBody = responseEntity.getBody();
        List<Map<String, Object>> candidates = getList(responseBody, "candidates");
        if (candidates.isEmpty())
            throw new AiResponseException("Lista candidates está vazia");

        Map<String, Object> content = getMap(candidates.getFirst(), "content");
        List<Map<String, String>> parts = getList(content, "parts");
        if (parts.isEmpty())
            throw new AiResponseException("Lista parts está vazia");

        String answer = parts.getFirst().get("text");
        if (answer == null || answer.isEmpty())
            throw new AiResponseException("resposta está vazia ou nula");
        return answer;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getList(Map<String, ?> map, String key) {
        Object value = map.get(key);
        return (value instanceof List) ? (List<T>) value : List.of();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMap(Map<String, ?> map, String key) {
        Object value = map.get(key);
        return (value instanceof Map<?, ?>) ? (Map<String, Object>) value : Map.of();
    }
}

package org.fatec.esportiva.ai;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.RoundingMode;

import org.fatec.esportiva.dto.request.AIDto;
import org.fatec.esportiva.dto.request.ProductDto;
import org.fatec.esportiva.dto.response.OrderResponseDto;
import org.fatec.esportiva.dto.response.TransactionResponseDto;
import org.fatec.esportiva.entity.enums.ProductStatus;
import org.fatec.esportiva.service.ProductService;
import org.fatec.esportiva.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class AI {
    // Usa a chave API declarada no arquivo .env (Esse arquivo não é versionado no
    // Git por questões de segurança)
    @Value("${API_KEY}")
    private String apiKey;
    private final String geminiApiBaseUrl = "https://generativelanguage.googleapis.com/v1beta/models/";
    private final String modelName = "gemini-2.0-flash";
    private final RestClient restClient;
    private final ProductService productService;
    private final TransactionService transactionService;

    public AI(RestClient.Builder builder, ProductService productService, TransactionService transactionService) {
        this.restClient = builder.baseUrl(geminiApiBaseUrl).build();
        this.productService = productService;
        this.transactionService = transactionService;
    }

    // Estou usando os exemplos REST
    // Exemplo para testar: https://aistudio.google.com/apikey
    // Exemplo de geração de texto:
    // https://ai.google.dev/gemini-api/docs/text-generation?hl=pt-br
    @SuppressWarnings("unchecked")
    @PostMapping("/admin/models")
    public String chat(@RequestBody AIDto aiDto) {
        HttpHeaders headers = new HttpHeaders();
        // Define o content type: -H 'Content-Type: application/json' \
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<String> chatHistory = aiDto.message();

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
        userText += "E na Consulta do usuário: " + chatHistory.get(chatHistory.size() - 1);

        String requestBody = String.format("""
                {
                "system_instruction": {
                "parts": [
                    {
                    "text": "Você é o assistente virtual de recomendações de produtos do e-commerce 'Esportiva'.
                    Essa loja vende produtos esportivos
                    Qualquer outra pergunta não relacionada deve ser ignorada"
                    }
                ]
                },
                  "contents": [{
                    "parts": [{"text": "%s"}]
                  }]
                }
                """, userText);

        // Depurar a API
        // System.out.println(String.format("Enviando para a API do Gemini: {%s}",
        // MediaType.APPLICATION_JSON));
        // System.out.println(String.format("Enviando para a API do Gemini: {%s}",
        // requestBody));

        // O método .post é equivalente ao: -X POST \
        ResponseEntity<Map<String, Object>> responseEntity = restClient.post() // ResponseEntity<Map> pois a resposta é
                                                                               // JSON
                .uri(uriBuilder -> uriBuilder.path(modelName + ":generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .headers(h -> h.addAll(headers))
                .body(requestBody)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String, Object>>() {
                });

        // Depurar resposta
        // System.out.println(String.format("Enviando para a API do Gemini: {%s}",
        // responseEntity.getBody()));

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            Map<String, Object> responseBody = responseEntity.getBody();

            if (responseBody.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
                if (!candidates.isEmpty() && candidates.get(0).containsKey("content")) {
                    Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                    if (content.containsKey("parts")) {
                        List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");
                        if (!parts.isEmpty() && parts.get(0).containsKey("text")) {
                            return parts.get(0).get("text"); // Extrai e retorna o texto
                        }
                    }
                }
            }
            return "Resposta da API do Gemini não pôde ser processada.";
        } else {
            return "Falha ao chamar a API do Gemini. Status: " + responseEntity.getStatusCode();
        }
    }

    private String getAvailableProducts() {
        // Define o título dos produtos disponíveis e como a IA vai interpretar a tabela
        String availableProducts = "Na seguinte lista de produtos (Nome|Preço|Descrição):\n";

        // Remove todos os atributos indisponíveis (Pelo Status e quantidade no estoque)
        List<ProductDto> productList = productService.getProducts("", ProductStatus.ATIVO, 100000, null);
        List<ProductDto> filteredList = productList.stream()
                .filter(product -> (product.getStockQuantity() - product.getBlockedQuantity()) > 0)
                .collect(Collectors.toList());

        // Obtém somente os atributos relevantes: Nome | Preço | Descrição
        for (ProductDto product : filteredList) {
            availableProducts += product.getName() + "|"
                    + product.getCostValue().multiply(product.getProfitMargin().add(BigDecimal.ONE))
                            .setScale(2, RoundingMode.HALF_UP).toString()
                    + "|"
                    + product.getDescription() + "\n";
        }

        return availableProducts;
    }

    private String getClientHistory() {
        // Define o título da seção do histórico do cliente e como a IA vai interpretar
        // a tabela
        String clientHistory = "No histórico do cliente (Nome|Quantidade comprada):\n";
        Map<String, Integer> productsHistory = new HashMap<>();

        // Obtém todos os produtos que estão no fluxo de compras (Do carrinho até a casa
        // do cliente)
        // Esses produtos são considerados compras efetuadas (O pipeline de devolução
        // pode se considerar 'compra não efetuada')
        List<TransactionResponseDto> clientTransactions = transactionService.getTransactions();
        for (TransactionResponseDto transaction : clientTransactions) {
            for (OrderResponseDto order : transaction.getOrders().getDeliveredOrders()) {
                String productName = order.getProduct().getName();
                Integer productQuantity = order.getQuantity();
                productsHistory.put(productName, productsHistory.getOrDefault(productName, 0) + productQuantity);
            }
        }

        // Converte os valores obtidos para a string
        for (Map.Entry<String, Integer> element : productsHistory.entrySet()) {
            clientHistory += element.getKey() + "|" + element.getValue() + "\n";
        }

        return clientHistory;
    }
}

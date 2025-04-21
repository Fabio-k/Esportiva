package org.fatec.esportiva.ai;

import java.util.List;
import java.util.Map;

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
    // Usa a chave API declarada no arquivo .env (Esse arquivo não é versionado no Git por questões de segurança)
    @Value("${API_KEY}")
    private String apiKey; 
    private final String geminiApiBaseUrl = "https://generativelanguage.googleapis.com/v1beta/models/";
    private final String modelName = "gemini-2.0-flash";
    private final RestClient restClient;

    public AI(RestClient.Builder builder) {
        this.restClient = builder.baseUrl(geminiApiBaseUrl).build();
    }

    // Estou usando os exemplos REST
    // Exemplo para testar: https://aistudio.google.com/apikey
    // Exemplo de geração de texto:
    // https://ai.google.dev/gemini-api/docs/text-generation?hl=pt-br
    @SuppressWarnings("unchecked")
    @PostMapping("/admin/models")
    public String chat(@RequestBody String userText) {
        HttpHeaders headers = new HttpHeaders();
        // Define o content type: -H 'Content-Type: application/json' \
        headers.setContentType(MediaType.APPLICATION_JSON);

        userText = """
                Recomende produtos da seguinte lista com base na consulta do usuário:
                Nome: Camiseta Algodão, Preço: 29.99, Descrição: Camiseta básica de algodão macio.
                Nome: Calça Jeans Slim, Preço: 79.99, Descrição: Calça jeans corte slim fit.
                Nome: Tênis Esportivo, Preço: 129.99, Descrição: Tênis confortável para corrida e treino.
                Consulta do usuário: Preciso de algo confortável para usar no dia a dia.
                """;

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
}

package org.fatec.esportiva.controller.api;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.fatec.esportiva.dto.request.AIDto;
import org.fatec.esportiva.service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIApiController {
    private final AiService aiService;

    @PostMapping("/message")
    public ResponseEntity<Map<String, Object>> sendAIMessage(@RequestBody AIDto aiDto) throws Exception {
        String aiResponse = aiService.getRecommendationAnswer(aiDto.message());

        Map<String, Object> response = new HashMap<>();
        response.put("reply", "AI: " + aiResponse);
        return ResponseEntity.ok(response);
    }
}
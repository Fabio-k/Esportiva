package org.fatec.esportiva.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model){
        model.addAttribute("error", ex.getMessage());
        return "error/custom-error";
    }

    @ExceptionHandler(AiResponseException.class)
    public ResponseEntity<String> handleAiResponseException(AiResponseException exception){
        log.error("Erro no processamento da mensagem da IA: ", exception);
        return ResponseEntity.badRequest().body("Não foi possível gerar a resposta tente novamente mais tarde");
    }
}

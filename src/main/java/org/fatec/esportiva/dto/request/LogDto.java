package org.fatec.esportiva.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class LogDto {
    private Long id;

    @NotBlank(message = "Usuário não pode ficar em branco")
    private String user;

    @NotNull(message = "Data e hora não pode ficar em branco")
    @PastOrPresent(message = "As operações no banco não podem ser efetuadas no futuro")
    private LocalDateTime timestamp;

    @NotBlank(message = "Tipo de operação não pode ficar em branco")
    private String operation;

    @NotBlank(message = "Conteúdo da operação não pode ficar em branco")
    private String operationContent;

    // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
    public String displayLogTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return timestamp.format(formatter);
    }
}

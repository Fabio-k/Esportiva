package org.fatec.esportiva.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

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
}

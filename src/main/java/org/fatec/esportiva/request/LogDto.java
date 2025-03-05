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

    @NotBlank(message = "Log: Usuário não pode ficar em branco")
    private String usuario;

    @NotNull(message = "Log: Data e hora não pode ficar em branco")
    @PastOrPresent(message = "Log: As operações no banco não podem ser efetuadas no futuro")
    private LocalDateTime data_hora;

    @NotBlank(message = "Log: Tipo de operação não pode ficar em branco")
    private String operacao;

    @NotBlank(message = "Log: Conteúdo da operação não pode ficar em branco")
    private String conteudo_alteracao;
}

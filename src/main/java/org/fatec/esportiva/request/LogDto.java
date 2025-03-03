package org.fatec.esportiva.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class LogDto {

    @NotBlank
    private int id;

    @NotBlank
    private String operacao;

    @NotBlank
    private String usuario;

    @PastOrPresent(message = "As operações no banco não podem ser efetuadas no futuro!")
    @NotBlank
    private LocalDateTime data_hora;

    @NotBlank
    private String conteudo_alteracao;
}

package org.fatec.esportiva.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.Builder.Default;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.enums.OrderStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {
    private Long id;

    @NotNull(message = "A compra do carrinho não pode ter data em branco")
    @PastOrPresent(message = "A compra do carrinho somente pode ser efetuada até o presente momento")
    private LocalDate purchaseDate;

    @NotNull(message = "Status não pode ficar em branco")
    private OrderStatus status;

    @NotNull(message = "Todo carrinho tem pelo menos um pedido associado")
    @Default
    private List<Order> orders = new ArrayList<>();

    public String displayEntryDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return purchaseDate.format(formatter);
    }

    // Máquina de estados que controla a transições conforme cada aprovação
    public OrderStatus changeState(boolean approve) {
        if (status == OrderStatus.CARRINHO_COMPRAS) {
            if (approve == true) {
                status = OrderStatus.EM_PROCESSAMENTO;
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }

            // O cartão de crédito não aprova, ele só reprova se for inválido o cartão
        } else if (status == OrderStatus.EM_PROCESSAMENTO) {
            if (approve == true) {
                status = OrderStatus.EM_TRANSITO;
                // Dá a baixa no estoque aqui
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }
        } else if (status == OrderStatus.EM_TRANSITO) {
            if (approve == true) {
                status = OrderStatus.ENTREGUE;
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }
        } else if (status == OrderStatus.ENTREGUE) {
            if (approve == true) {
                status = OrderStatus.EM_TROCA;
                // Aparece um aviso para o cliente, pode ser uma lista de produtos que estão
                // nesse estado
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }
        } else if (status == OrderStatus.EM_TROCA) {
            if (approve == true) {
                status = OrderStatus.TROCADO;
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
                // O produto volta para o estoque
            }
        } else if (status == OrderStatus.TROCADO) {
            // Não faz nada
        }

        return status;
    }
}

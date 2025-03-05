package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "transacoes")
public class Transactions {

    @Id
    @Column(name = "tra_id")
    private Long id;

    @NotNull
    @Column(name = "tra_data_compra")
    private Date purchaseDate;

    @ManyToOne
    @JoinColumn(name = "tra_cli_id")
    private Client client;
}

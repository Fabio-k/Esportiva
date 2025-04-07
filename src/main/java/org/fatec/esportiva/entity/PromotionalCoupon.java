package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="cupons_promocionais")
@Entity
@Getter
@Setter
public class PromotionalCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cpp_id")
    private Long id;

    @Column(name = "cpp_codigo")
    private String code;

    @Column(name = "cpp_desconto")
    private Integer discount;
}

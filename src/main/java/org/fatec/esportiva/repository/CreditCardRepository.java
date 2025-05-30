package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findAllByIdInAndClientId(List<Long> ids, Long clientId);
}

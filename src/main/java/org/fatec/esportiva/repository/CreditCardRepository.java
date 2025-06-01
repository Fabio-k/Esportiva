package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findAllByIdInAndClientId(List<Long> ids, Long clientId);

    Optional<CreditCard> findByIdAndClientId(Long id, Long clientId);
}

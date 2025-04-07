package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.ExchangeVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeVoucherRepository extends JpaRepository<ExchangeVoucher, Long> {
    List<ExchangeVoucher> findAllByIdInAndClientId(List<Long> ids, Long clientId);
}

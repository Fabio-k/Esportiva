package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.address.Cep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CepRepository extends JpaRepository<Cep, Long> {
     Optional<Cep> findByCep(String cep);
}

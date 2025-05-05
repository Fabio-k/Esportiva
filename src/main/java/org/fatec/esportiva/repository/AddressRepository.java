package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByTemporaryTrueAndExpiredAtBefore(LocalDateTime createdAt);
}

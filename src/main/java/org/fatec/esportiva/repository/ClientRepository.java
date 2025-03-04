package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<UserDetails> findUserByEmail(String email);
}

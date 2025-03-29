package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<UserDetails> findUserByEmail(String email);

    Optional<Client> findClientByEmail(String email);

    @Query("SELECT c FROM Client c WHERE (:name is NULL or c.name LIKE :name) AND (:email is NULL or c.email LIKE :email) AND (:cpf is NULL or c.cpf = :cpf) AND (:status IS NULL OR c.status = :status) AND (:gender IS NULL OR c.gender = :gender)")
    List<Client> findWithFilter(String name, String email, String cpf, UserStatus status, Gender gender);


}

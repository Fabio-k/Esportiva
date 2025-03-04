package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<UserDetails> findUserByEmail(String email);
}

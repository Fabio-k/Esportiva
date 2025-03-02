package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.User;
import org.fatec.esportiva.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDetails> findUserByEmail(String email);
    Optional<User> findByCode(String code);
    List<User> findAllByRole(Role role);
}

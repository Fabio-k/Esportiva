package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.PromotionalCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionalCouponRepository extends JpaRepository<PromotionalCoupon, Long> {
    Optional<PromotionalCoupon> findByCode(String code);
}

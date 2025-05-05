package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.address.AddressCategory;
import org.fatec.esportiva.entity.address.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AddressCategoryRepository extends JpaRepository<AddressCategory, Long> {
    Set<AddressCategory> findByAddressTypeIn(Set<AddressType> categories);
}

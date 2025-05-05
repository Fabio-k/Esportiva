package org.fatec.esportiva.sheduledTasks;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.address.Address;
import org.fatec.esportiva.repository.AddressRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressTask {
    private final AddressRepository addressRepository;

    @Scheduled(fixedRate = 60000 * 60 * 24)
    public void freeTemporaryAddress(){
        List<Address> addresses = addressRepository.findByTemporaryTrueAndExpiredAtBefore(LocalDateTime.now());
        addresses.forEach(addressRepository::delete);
    }
}

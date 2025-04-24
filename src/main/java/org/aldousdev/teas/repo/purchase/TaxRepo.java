package org.aldousdev.teas.repo.purchase;

import org.aldousdev.teas.models.purchase.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TaxRepo extends JpaRepository <TaxRate, Long>{
    Optional<TaxRate> findByState(String state);


}

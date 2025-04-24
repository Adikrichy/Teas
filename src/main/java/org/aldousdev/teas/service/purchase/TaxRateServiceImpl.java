package org.aldousdev.teas.service.purchase;

import org.aldousdev.teas.models.purchase.TaxRate;
import org.aldousdev.teas.repo.purchase.TaxRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxRateServiceImpl implements TaxRateService {
    @Autowired
    private final TaxRepo taxRepo;

    public TaxRateServiceImpl(TaxRepo taxRepo) {
        this.taxRepo = taxRepo;
    }

    @Override
    public Double getTaxRateByState(String state) {
//        return taxRepo.findRateByState(state).orElse(0.0);
        TaxRate taxRate = taxRepo.findByState(state).orElse(null);
        return taxRate == null ? 0.0 : taxRate.getRate();


    }
}

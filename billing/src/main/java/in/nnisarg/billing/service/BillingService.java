package in.nnisarg.billing.service;

import in.nnisarg.billing.model.Billing;
import in.nnisarg.billing.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    // Create or Update a Billing Record
    public Billing saveBilling(Billing billing) {
        return billingRepository.save(billing);
    }

    // Retrieve all Billing Records
    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    // Retrieve a Billing Record by ID
    public Optional<Billing> getBillingById(Long id) {
        return billingRepository.findById(id);
    }

    // Delete a Billing Record by ID
    public void deleteBilling(Long id) {
        billingRepository.deleteById(id);
    }
}

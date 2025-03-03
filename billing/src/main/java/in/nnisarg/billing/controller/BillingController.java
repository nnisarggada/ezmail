package in.nnisarg.billing.controller;

import in.nnisarg.billing.model.Billing;
import in.nnisarg.billing.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    // Create or Update Billing Record
    @PostMapping("/save")
    public Billing saveBilling(@RequestBody Billing billing) {
        return billingService.saveBilling(billing);
    }

    // Retrieve All Billing Records
    @GetMapping("/all")
    public List<Billing> getAllBillings() {
        return billingService.getAllBillings();
    }

    // Retrieve a Billing Record by ID
    @GetMapping("/{id}")
    public Optional<Billing> getBillingById(@PathVariable Long id) {
        return billingService.getBillingById(id);
    }

    // Delete a Billing Record by ID
    @DeleteMapping("/{id}")
    public String deleteBilling(@PathVariable Long id) {
        billingService.deleteBilling(id);
        return "Billing record deleted successfully.";
    }
}

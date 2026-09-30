package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.LoanPayment;
import org.example.dbproject.service.LoanPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan-payments")
@CrossOrigin(origins = {"http://localhost:8080"})
public class LoanPaymentController {

    private final LoanPaymentService loanPaymentService;

    @PostMapping
    public ResponseEntity<LoanPayment> createLoanPayment(
            @RequestBody LoanPayment loanPayment) {

        return ResponseEntity.ok(
                loanPaymentService.save(loanPayment)
        );
    }

    @GetMapping
    public ResponseEntity<List<LoanPayment>> getAllLoanPayments() {

        return ResponseEntity.ok(
                loanPaymentService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanPayment> getLoanPaymentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                loanPaymentService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanPayment> updateLoanPayment(
            @PathVariable Long id,
            @RequestBody LoanPayment loanPayment) {

        loanPayment.setPaymentId(id);

        return ResponseEntity.ok(
                loanPaymentService.save(loanPayment)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanPayment(
            @PathVariable Long id) {

        loanPaymentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.Loan;
import org.example.dbproject.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loans")
@CrossOrigin(origins = {"http://localhost:8080"})
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<Loan> createLoan(
            @RequestBody Loan loan) {

        return ResponseEntity.ok(
                loanService.save(loan)
        );
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {

        return ResponseEntity.ok(
                loanService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                loanService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(
            @PathVariable Long id,
            @RequestBody Loan loan) {

        loan.setLoanId(id);

        return ResponseEntity.ok(
                loanService.save(loan)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(
            @PathVariable Long id) {

        loanService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
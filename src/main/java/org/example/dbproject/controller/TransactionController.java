package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.Transaction;
import org.example.dbproject.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@CrossOrigin(origins = {"http://localhost:8080"})
public class TransactionController {

    private final TransactionService transactionService;

    // CREATE
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @RequestBody Transaction transaction) {

        return ResponseEntity.ok(
                transactionService.save(transaction)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {

        return ResponseEntity.ok(
                transactionService.findAll()
        );
    }

    // GET ONE
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                transactionService.findById(id)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @RequestBody Transaction transaction) {

        transaction.setId(id);

        return ResponseEntity.ok(
                transactionService.save(transaction)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Long id) {

        transactionService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.Account;
import org.example.dbproject.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:8080")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestBody Account account) {

        Account savedAccount = accountService.save(account);

        return ResponseEntity.ok(savedAccount);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {

        List<Account> accounts = accountService.findAll();

        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(
            @PathVariable Long id) {

        Account account = accountService.findById(id);

        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(
            @PathVariable Long id,
            @RequestBody Account account) {

        account.setId(id);

        Account updatedAccount = accountService.save(account);

        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable Long id) {

        accountService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
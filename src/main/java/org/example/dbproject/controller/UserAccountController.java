package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.UserAccount;
import org.example.dbproject.service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-accounts")
@CrossOrigin(origins = "http://localhost:8080")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping
    public ResponseEntity<UserAccount> createUserAccount(
            @RequestBody UserAccount userAccount) {

        UserAccount savedUserAccount =
                userAccountService.save(userAccount);

        return ResponseEntity.ok(savedUserAccount);
    }

    @GetMapping
    public ResponseEntity<List<UserAccount>> getAllUserAccounts() {

        List<UserAccount> userAccounts =
                userAccountService.findAll();

        return ResponseEntity.ok(userAccounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccount> getUserAccountById(
            @PathVariable Long id) {

        UserAccount userAccount =
                userAccountService.findById(id);

        return ResponseEntity.ok(userAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAccount> updateUserAccount(
            @PathVariable Long id,
            @RequestBody UserAccount userAccount) {

        userAccount.setUserId(id);

        UserAccount updatedUserAccount =
                userAccountService.save(userAccount);

        return ResponseEntity.ok(updatedUserAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAccount(
            @PathVariable Long id) {

        userAccountService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.Beneficiary;
import org.example.dbproject.service.BeneficiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beneficiaries")
@CrossOrigin(origins = {"http://localhost:8080"})
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    @PostMapping
    public ResponseEntity<Beneficiary> createBeneficiary(
            @RequestBody Beneficiary beneficiary) {

        return ResponseEntity.ok(
                beneficiaryService.save(beneficiary)
        );
    }

    @GetMapping
    public ResponseEntity<List<Beneficiary>> getAllBeneficiaries() {

        return ResponseEntity.ok(
                beneficiaryService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficiary> getBeneficiaryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                beneficiaryService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beneficiary> updateBeneficiary(
            @PathVariable Long id,
            @RequestBody Beneficiary beneficiary) {

        beneficiary.setBeneficiaryId(id);

        return ResponseEntity.ok(
                beneficiaryService.save(beneficiary)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeneficiary(
            @PathVariable Long id) {

        beneficiaryService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
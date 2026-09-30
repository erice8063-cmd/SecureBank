package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.Branch;
import org.example.dbproject.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
@CrossOrigin(origins = {"http://localhost:8080"})
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<Branch> createBranch(
            @RequestBody Branch branch) {

        return ResponseEntity.ok(
                branchService.save(branch)
        );
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {

        return ResponseEntity.ok(
                branchService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                branchService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(
            @PathVariable Long id,
            @RequestBody Branch branch) {

        branch.setBranchId(id);

        return ResponseEntity.ok(
                branchService.save(branch)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(
            @PathVariable Long id) {

        branchService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
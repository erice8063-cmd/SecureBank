package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.Customer;
import org.example.dbproject.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@CrossOrigin(origins = {"http://localhost:8080"})
public class CustomerController {

    private final CustomerService customerService;

    // CREATE
    @PostMapping
    public ResponseEntity<Customer> createCustomer(
            @RequestBody Customer customer) {

        return ResponseEntity.ok(
                customerService.save(customer)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {

        return ResponseEntity.ok(
                customerService.findAll()
        );
    }

    // GET ONE
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                customerService.findById(id)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customer) {

        customer.setCustomer_id(id);

        return ResponseEntity.ok(
                customerService.save(customer)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
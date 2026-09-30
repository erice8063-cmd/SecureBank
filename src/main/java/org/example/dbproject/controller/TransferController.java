package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.TransferRequest;
import org.example.dbproject.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transfers")
@CrossOrigin(origins = {"http://localhost:8080"})
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<String> transfer(
            @RequestBody TransferRequest request) {

        transferService.transfer(
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount()
        );

        return ResponseEntity.ok("Transfer completed successfully");
    }
}
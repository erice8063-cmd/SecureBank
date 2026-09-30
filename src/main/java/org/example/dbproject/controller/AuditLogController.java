package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.AuditLog;
import org.example.dbproject.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/audit-logs")
@CrossOrigin(origins = {"http://localhost:8080"})
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<AuditLog> createAuditLog(
            @RequestBody AuditLog auditLog) {

        return ResponseEntity.ok(
                auditLogService.save(auditLog)
        );
    }

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {

        return ResponseEntity.ok(
                auditLogService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLog> getAuditLogById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                auditLogService.findById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuditLog> updateAuditLog(
            @PathVariable Long id,
            @RequestBody AuditLog auditLog) {

        auditLog.setLogId(id);

        return ResponseEntity.ok(
                auditLogService.save(auditLog)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuditLog(
            @PathVariable Long id) {

        auditLogService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
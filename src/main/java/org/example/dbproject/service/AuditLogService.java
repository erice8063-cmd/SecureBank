package org.example.dbproject.service;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.AuditLog;
import org.example.dbproject.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;

    public List<AuditLog> findAll() {
        return auditLogRepository.findAll();
    }
    public AuditLog findById(Long id) {
        return auditLogRepository.findById(id)
                .orElse(null);
    }
    public AuditLog save(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }
    public void deleteById(Long id) {
        auditLogRepository.deleteById(id);
    }
}

package org.example.dbproject.repository;
import org.example.dbproject.entity.AuditLog;
import org.example.dbproject.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUserAccount(UserAccount userAccount);

}
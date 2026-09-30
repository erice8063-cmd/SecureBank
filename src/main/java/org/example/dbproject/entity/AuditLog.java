package org.example.dbproject.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "audit_log")

public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    private String action;
    private String entity;

    @Column(name = "ip_address")
    private String ipAddress;
}

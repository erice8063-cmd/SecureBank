package org.example.dbproject.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@Table (name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotBlank(message = "Username is required")
    @Column(nullable = false, unique = true)
    private String username;

    private String password_hash;
    private String role;

    @Column(nullable = false)
    private boolean mfa = false;

    private LocalDateTime last_login;

    @OneToMany(mappedBy = "userAccount")
    private List<AuditLog> auditLogs;
}
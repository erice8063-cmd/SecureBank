package org.example.dbproject.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "beneficiary")

public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beneficiaryId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private String name;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank_name")
    private String bank_name;

    private LocalDate dateOfBirth;
    private LocalDateTime createdAt = LocalDateTime.now();
}

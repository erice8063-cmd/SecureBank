package org.example.dbproject.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "loan_payment")

public class LoanPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Column(precision = 18, scale = 2)
    private BigDecimal amount;

    private LocalDate due_date;
    private LocalDate paid_date;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
}

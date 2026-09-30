package org.example.dbproject.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "loan")

public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    private String loan_type;
    private String status;

    @Column(precision = 18, scale = 2)
    private BigDecimal principal;
    @Column(precision = 5, scale = 2)
    private BigDecimal interest_rate;
    private int term_months;

    private String applied_at;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "loan")
    private List<LoanPayment> loanPayments;
}

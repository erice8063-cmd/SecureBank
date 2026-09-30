package org.example.dbproject.repository;
import org.example.dbproject.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Long> {
}

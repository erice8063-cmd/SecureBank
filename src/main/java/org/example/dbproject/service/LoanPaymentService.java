package org.example.dbproject.service;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.LoanPayment;
import org.example.dbproject.repository.LoanPaymentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanPaymentService {
    private final LoanPaymentRepository loanPaymentRepository;

    public List<LoanPayment> findAll() {
        return loanPaymentRepository.findAll();
    }
    public LoanPayment findById(Long id) {
        return loanPaymentRepository.findById(id)
                .orElse(null);
    }
    public LoanPayment save(LoanPayment loanPayment) {
        return loanPaymentRepository.save(loanPayment);
    }
    public void deleteById(Long id) {
        loanPaymentRepository.deleteById(id);
    }
}

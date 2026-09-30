package org.example.dbproject.service;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.Loan;
import org.example.dbproject.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }
    public Loan findById(Long id) {
        return loanRepository.findById(id)
                .orElse(null);
    }
    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }
    public void deleteById(Long id) {
        loanRepository.deleteById(id);
    }
}

package org.example.dbproject.service;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.Transaction;
import org.example.dbproject.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElse(null);
    }
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }
}

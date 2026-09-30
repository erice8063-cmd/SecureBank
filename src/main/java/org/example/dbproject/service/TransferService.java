package org.example.dbproject.service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.example.dbproject.entity.Account;
import org.example.dbproject.entity.Transaction;
import org.example.dbproject.entity.enums.TransactionType;
import org.example.dbproject.repository.AccountRepository;
import org.example.dbproject.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void transfer(Long senderId, Long receiverId, BigDecimal amount) {
        if (senderId == null || receiverId == null) {
            throw new IllegalArgumentException("Both account IDs are required");
        }

        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException(
                    "Cannot transfer to the same account"
            );
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Transfer amount must be greater than zero"
            );
        }

        Account sender = accountRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sender account not found"
                ));

        Account receiver = accountRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Receiver account not found"
                ));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        LocalDateTime transferTime = LocalDateTime.now();

        Transaction outgoing = new Transaction();
        outgoing.setAccount(sender);
        outgoing.setAmount(amount.negate());
        outgoing.setType(TransactionType.TRANSFER);
        outgoing.setTimestamp(transferTime);

        Transaction incoming = new Transaction();
        incoming.setAccount(receiver);
        incoming.setAmount(amount);
        incoming.setType(TransactionType.TRANSFER);
        incoming.setTimestamp(transferTime);

        transactionRepository.save(outgoing);
        transactionRepository.save(incoming);
    }
}
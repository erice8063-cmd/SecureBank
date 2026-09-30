package org.example.dbproject.service;
import java.math.BigDecimal;
import org.example.dbproject.entity.Transaction;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class FraudAiService {

    // Example project threshold; adjust it to suit your requirements.
    private static final BigDecimal REVIEW_THRESHOLD =
            new BigDecimal("10000.00");

    private final TransactionService transactionService;
    private final ChatClient chatClient;

    public FraudAiService(
            TransactionService transactionService,
            ChatClient.Builder chatClientBuilder
    ) {
        this.transactionService = transactionService;
        this.chatClient = chatClientBuilder.build();
    }

    public FraudReview reviewTransaction(Long transactionId) {
        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction ID is required");
        }

        Transaction transaction = transactionService.findById(transactionId);

        if (transaction == null) {
            throw new IllegalArgumentException("Transaction not found");
        }

        // Your transfer service uses a negative amount for outgoing transfers.
        BigDecimal amount = transaction.getAmount().abs();
        boolean requiresReview =
                amount.compareTo(REVIEW_THRESHOLD) >= 0;

        if (!requiresReview) {
            return new FraudReview(
                    transactionId,
                    false,
                    "This transaction did not meet the example review threshold."
            );
        }

        String explanation = chatClient.prompt()
                .system("""
                        You assist a bank employee reviewing transactions.
                        Explain why the provided transaction was flagged by
                        a simple amount threshold. Keep the explanation brief.
                        An amount alone does not establish fraud. Do not claim
                        to have checked account history, customer identity,
                        or other transactions. Do not recommend automatically
                        blocking the account or reversing the transaction.
                        """)
                .user("Transaction type: " + transaction.getType()
                        + "\nAbsolute amount: " + amount
                        + "\nReview threshold: " + REVIEW_THRESHOLD)
                .call()
                .content();

        return new FraudReview(
                transactionId,
                true,
                explanation
        );
    }

    public record FraudReview(
            Long transactionId,
            boolean requiresReview,
            String explanation
    ) {}
}

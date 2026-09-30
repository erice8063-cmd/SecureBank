package org.example.dbproject.service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class TransactionAiService {

    private final ChatClient chatClient;

    public TransactionAiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String answerTransactionQuestion(String question) {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("Question cannot be blank");
        }

        return chatClient.prompt()
                .system("""
                        You are a helpful assistant for SecureBank.
                        Explain general banking transaction concepts, including
                        deposits, withdrawals, transfers, and transaction history.

                        Do not claim to have viewed a customer's account or
                        transaction records. Do not claim to have completed,
                        canceled, or changed a transaction. If a question requires
                        account-specific information, explain that you cannot
                        access it through this request.
                        """)
                .user(question)
                .call()
                .content();
    }
}
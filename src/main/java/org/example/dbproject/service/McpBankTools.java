package org.example.dbproject.service;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class McpBankTools {

    @Tool(description = "List the banking topics supported by SecureBank")
    public List<String> listBankingTopics() {
        return List.of(
                "Checking accounts",
                "Savings accounts",
                "Transactions",
                "Transfers",
                "Loans"
        );
    }

    @Tool(description = "Explain how transfers work in the SecureBank project")
    public String explainTransfers() {
        return """
                A transfer moves a simulated amount from one SecureBank account
                to another. The accounts must be different, the amount must be
                greater than zero, and the sender must have sufficient funds.
                A successful transfer updates both balances and records an
                outgoing and incoming transaction.
                """;
    }

    @Tool(description = "Check whether an amount is valid for a transfer; does not perform a transfer")
    public String checkTransferAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return "The transfer amount must be greater than zero.";
        }

        return "The amount is positive. Account existence and available "
                + "funds still need to be checked before a transfer.";
    }
}
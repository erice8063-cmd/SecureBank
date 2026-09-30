package org.example.dbproject.controller;
import java.util.List;
import java.util.Vector;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final ChatClient chatClient;

    private final Vector<String> topics = new Vector<>(
            List.of("Checking accounts", "Savings accounts", "Transfers", "Loans")
    );

    public AiController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/ask")
    public ResponseEntity<AskResponse> ask(@RequestBody AskRequest request) {
        if (request == null
                || request.message() == null
                || request.message().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        String answer = chatClient.prompt()
                .system("""
                        You are a helpful assistant for a banking application.
                        Give general explanations about banking features.
                        Do not claim to have accessed account information or
                        completed a transaction.
                        """)
                .user(request.message())
                .call()
                .content();

        return ResponseEntity.ok(new AskResponse(answer));
    }

    @GetMapping("/topics")
    public Vector<String> getTopics() {
        return topics;
    }

    public record AskRequest(String message) {}

    public record AskResponse(String answer) {}
}
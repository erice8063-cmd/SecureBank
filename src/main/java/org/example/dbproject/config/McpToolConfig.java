package org.example.dbproject.config;
import org.example.dbproject.service.McpBankTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolConfig {

    @Bean
    public ToolCallbackProvider bankToolProvider(McpBankTools bankTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(bankTools)
                .build();
    }
}
package org.example.dbproject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(
        JwtConfiguration.JwtProperties.class
)
public class JwtConfiguration {

    @ConfigurationProperties(prefix = "jwt")
    public record JwtProperties(
            String secret,
            long expiration
    ) {
    }
}
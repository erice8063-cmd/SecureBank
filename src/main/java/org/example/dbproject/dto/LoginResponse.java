package org.example.dbproject.dto;

public record LoginResponse(
        String token,
        String username,
        String role,
        String dashboardUrl
) {
}
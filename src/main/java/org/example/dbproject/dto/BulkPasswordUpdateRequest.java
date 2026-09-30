package org.example.dbproject.dto;

public record BulkPasswordUpdateRequest(
        Long id,
        String password
) {
}
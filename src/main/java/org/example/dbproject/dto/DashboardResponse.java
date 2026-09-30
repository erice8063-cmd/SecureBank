package org.example.dbproject.dto;

import org.example.dbproject.entity.enums.RoleType;

import java.util.Map;

public record DashboardResponse(
        Long userId,
        String name,
        String username,
        String email,
        RoleType role,
        String message,
        Map<String, Long> statistics
) {
}
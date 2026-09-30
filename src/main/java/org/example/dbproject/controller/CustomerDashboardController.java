package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.dto.DashboardResponse;
import org.example.dbproject.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerDashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public DashboardResponse dashboard(
            Authentication authentication
    ) {
        return dashboardService.getCustomerDashboard(
                authentication.getName()
        );
    }
}
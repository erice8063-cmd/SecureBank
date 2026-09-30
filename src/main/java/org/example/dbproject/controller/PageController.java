package org.example.dbproject.controller;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/customer-dashboard")
    public String customerDashboard(Authentication authentication) {
        return dashboardFor(authentication);
    }

    @GetMapping("/teller-dashboard")
    public String tellerDashboard(Authentication authentication) {
        return dashboardFor(authentication);
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard(Authentication authentication) {
        return dashboardFor(authentication);
    }

    private String dashboardFor(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean teller = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TELLER"));

        if (admin) {
            return "admin-dashboard";
        }

        if (teller) {
            return "teller-dashboard";
        }

        return "customer-dashboard";
    }

    // Keep your other page mappings here.
}
package org.example.dbproject.service;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.dto.DashboardResponse;
import org.example.dbproject.entity.User;
import org.example.dbproject.entity.enums.RoleType;
import org.example.dbproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public DashboardResponse getCustomerDashboard(
            String email
    ) {
        User user = userService.findByEmail(email);

        verifyRole(user, RoleType.CUSTOMER);

        Map<String, Long> statistics =
                new LinkedHashMap<>();

        /*
         * These values can be replaced with actual account
         * and transaction repository queries later.
         */
        statistics.put("activeAccounts", 0L);
        statistics.put("pendingTransactions", 0L);
        statistics.put("activeLoans", 0L);
        statistics.put("scheduledPayments", 0L);

        return createResponse(
                user,
                "Welcome back. Your banking dashboard is ready.",
                statistics
        );
    }

    @Transactional(readOnly = true)
    public DashboardResponse getTellerDashboard(
            String email
    ) {
        User user = userService.findByEmail(email);

        verifyRole(user, RoleType.TELLER);

        Map<String, Long> statistics =
                new LinkedHashMap<>();

        statistics.put(
                "totalCustomers",
                userRepository.countByRole(
                        RoleType.CUSTOMER
                )
        );

        statistics.put("customersServed", 0L);
        statistics.put("transactionsProcessed", 0L);
        statistics.put("pendingReviews", 0L);

        return createResponse(
                user,
                "Your teller workspace is ready.",
                statistics
        );
    }

    @Transactional(readOnly = true)
    public DashboardResponse getAdminDashboard(
            String email
    ) {
        User user = userService.findByEmail(email);

        verifyRole(user, RoleType.ADMIN);

        Map<String, Long> statistics =
                new LinkedHashMap<>();

        statistics.put(
                "totalUsers",
                userRepository.count()
        );

        statistics.put(
                "totalCustomers",
                userRepository.countByRole(
                        RoleType.CUSTOMER
                )
        );

        statistics.put(
                "totalTellers",
                userRepository.countByRole(
                        RoleType.TELLER
                )
        );

        statistics.put(
                "totalAdmins",
                userRepository.countByRole(
                        RoleType.ADMIN
                )
        );

        return createResponse(
                user,
                "SecureBank administration is ready.",
                statistics
        );
    }

    private DashboardResponse createResponse(
            User user,
            String message,
            Map<String, Long> statistics
    ) {
        return new DashboardResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                message,
                statistics
        );
    }

    private void verifyRole(
            User user,
            RoleType requiredRole
    ) {
        if (user.getRole() != requiredRole) {
            throw new IllegalArgumentException(
                    "User does not have the required role"
            );
        }
    }
}
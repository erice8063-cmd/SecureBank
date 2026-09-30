package org.example.dbproject.config;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.User;
import org.example.dbproject.entity.enums.RoleType;
import org.example.dbproject.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevelopmentUserConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createDevelopmentUsers() {
        return args -> {
            createCustomer();
            createTeller();
            createAdmin();
        };
    }

    private void createCustomer() {
        String email = "test@example.com";

        if (userRepository.existsByEmailIgnoreCase(email)) {
            return;
        }

        User user = new User();

        user.setName("Test Customer");
        user.setUsername("testcustomer");
        user.setEmail(email);

        user.setPassword(
                passwordEncoder.encode("password123")
        );

        user.setRole(RoleType.CUSTOMER);

        userRepository.save(user);

        System.out.println("DEVELOPMENT CUSTOMER CREATED");
        System.out.println("Email: test@example.com");
        System.out.println("Password: password123");
        System.out.println("Role: CUSTOMER");
    }

    private void createTeller() {
        String email = "teller@example.com";

        if (userRepository.existsByEmailIgnoreCase(email)) {
            return;
        }

        User user = new User();

        user.setName("Test Teller");
        user.setUsername("testteller");
        user.setEmail(email);

        user.setPassword(
                passwordEncoder.encode("password123")
        );

        user.setRole(RoleType.TELLER);

        userRepository.save(user);

        System.out.println("DEVELOPMENT TELLER CREATED");
        System.out.println("Email: teller@example.com");
        System.out.println("Password: password123");
        System.out.println("Role: TELLER");
    }

    private void createAdmin() {
        String email = "admin@example.com";

        if (userRepository.existsByEmailIgnoreCase(email)) {
            return;
        }

        User user = new User();

        user.setName("Test Administrator");
        user.setUsername("testadmin");
        user.setEmail(email);

        user.setPassword(
                passwordEncoder.encode("ChangeMe123!")
        );

        user.setRole(RoleType.ADMIN);

        userRepository.save(user);

        System.out.println("DEVELOPMENT ADMIN CREATED");
        System.out.println("Email: admin@example.com");
        System.out.println("Password: ChangeMe123!");
        System.out.println("Role: ADMIN");
    }
}
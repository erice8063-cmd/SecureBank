package org.example.dbproject;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final BankUserDetailsService bankUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                /*
                 * Disabled for the current learning project.
                 * Re-enable before using the application in
                 * production.
                 */
                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.configurationSource(
                        corsConfigurationSource()
                ))

                .userDetailsService(bankUserDetailsService)

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/access-denied",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico",
                                "/error"
                        ).permitAll()

                        /*
                         * Must appear before /api/users/**.
                         */
                        .requestMatchers(
                                "/api/auth/me",
                                "/api/users/me"
                        ).hasAnyRole(
                                "CUSTOMER",
                                "TELLER",
                                "ADMIN"
                        )

                        .requestMatchers(
                                "/api/customer/**"
                        ).hasRole("CUSTOMER")

                        .requestMatchers(
                                "/api/teller/**"
                        ).hasRole("TELLER")

                        .requestMatchers(
                                "/api/admin/**",
                                "/api/users",
                                "/api/users/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                "/api/customers/**",
                                "/api/user-accounts/**"
                        ).hasAnyRole(
                                "TELLER",
                                "ADMIN"
                        )

                        .requestMatchers(
                                "/home",
                                "/customer-dashboard",
                                "/teller-dashboard",
                                "/admin-dashboard",
                                "/banking-accounts",
                                "/customer-account-page",
                                "/loans",
                                "/payments",
                                "/transactions",
                                "/transfers",
                                "/user-page",
                                "/user-account-page",
                                "/api/accounts/**",
                                "/api/loans/**",
                                "/api/loan-payments/**",
                                "/api/transactions/**",
                                "/api/transfers/**"
                        ).hasAnyRole(
                                "CUSTOMER",
                                "TELLER",
                                "ADMIN"
                        )

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {

                            boolean isAdmin = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(authority ->
                                            authority.getAuthority()
                                                    .equals("ROLE_ADMIN")
                                    );

                            boolean isTeller = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(authority ->
                                            authority.getAuthority()
                                                    .equals("ROLE_TELLER")
                                    );

                            if (isAdmin) {
                                response.sendRedirect("/admin-dashboard");
                            } else if (isTeller) {
                                response.sendRedirect("/teller-dashboard");
                            } else {
                                response.sendRedirect("/customer-dashboard");
                            }
                        })
                        .failureUrl("/login?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                .exceptionHandling(exception ->
                        exception.accessDeniedPage(
                                "/access-denied"
                        )
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource
    corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        /*
         * Change this if your front end uses another port.
         */
        configuration.setAllowedOrigins(
                List.of("http://localhost:5500")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        /*
         * Required for JSESSIONID cookies.
         */
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
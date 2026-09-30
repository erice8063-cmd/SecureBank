package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.JwtService;
import org.example.dbproject.LoginRequest;
import org.example.dbproject.dto.LoginResponse;
import org.example.dbproject.entity.User;
import org.example.dbproject.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        String authenticatedUsername = authentication.getName();

        User user = userRepository.findByUsername(authenticatedUsername)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: "
                                        + authenticatedUsername
                        )
                );

        String token = jwtService.generateToken(user.getUsername());

        String dashboardUrl = switch (user.getRole()) {
            case CUSTOMER -> "/customer-dashboard";
            case TELLER -> "/teller-dashboard";
            case ADMIN -> "/admin-dashboard";
        };

        LoginResponse response = new LoginResponse(
                token,
                user.getUsername(),
                user.getRole().name(),
                dashboardUrl
        );

        return ResponseEntity.ok(response);
    }
}
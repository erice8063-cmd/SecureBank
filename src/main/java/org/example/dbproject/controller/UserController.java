package org.example.dbproject.controller;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.User;
import org.example.dbproject.entity.enums.RoleType;
import org.example.dbproject.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
     * Available to every authenticated role.
     *
     * authentication.getName() contains the email
     * because BankUserDetailsService uses the email
     * as the Spring Security username.
     */
    @GetMapping("/me")
    @PreAuthorize(
            "hasAnyRole('CUSTOMER', 'TELLER', 'ADMIN')"
    )
    public User currentUser(
            Authentication authentication
    ) {
        return userService.findByEmail(
                authentication.getName()
        );
    }

    /*
     * Administrator: retrieve all users.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> allUsers() {
        return userService.findAll();
    }

    /*
     * Administrator: retrieve one user.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User userById(
            @PathVariable Long id
    ) {
        return userService.findById(id);
    }

    /*
     * Administrator: update username and password.
     */
    @PutMapping("/{id}/credentials")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateCredentials(
            @PathVariable Long id,
            @RequestBody CredentialsRequest request
    ) {
        return userService.updateCredentials(
                id,
                request.getUsername(),
                request.getPassword()
        );
    }

    /*
     * Administrator: update only the email.
     */
    @PutMapping("/{id}/email")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateEmail(
            @PathVariable Long id,
            @RequestBody EmailRequest request
    ) {
        return userService.updateEmail(
                id,
                request.getEmail()
        );
    }

    /*
     * Administrator: change CUSTOMER, TELLER,
     * or ADMIN role.
     */
    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequest request
    ) {
        return userService.updateRole(
                id,
                request.getRole()
        );
    }

    public static class CredentialsRequest {

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class EmailRequest {

        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class RoleRequest {

        private RoleType role;

        public RoleType getRole() {
            return role;
        }

        public void setRole(RoleType role) {
            this.role = role;
        }
    }
}
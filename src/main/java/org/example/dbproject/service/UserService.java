package org.example.dbproject.service;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.User;
import org.example.dbproject.entity.enums.RoleType;
import org.example.dbproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {

        if (user.getPassword() != null
                && !user.getPassword().startsWith("$2")) {

            user.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
        }

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "Username is required"
            );
        }

        return userRepository
                .findByUsernameIgnoreCase(username.trim())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "Email is required"
            );
        }

        return userRepository
                .findByEmailIgnoreCase(
                        email.trim().toLowerCase()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        )
                );
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateCredentials(
            Long id,
            String username,
            String password
    ) {
        User user = findById(id);

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "Username is required"
            );
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "Password is required"
            );
        }

        String cleanedUsername = username.trim();

        if (!user.getUsername().equalsIgnoreCase(cleanedUsername)
                && userRepository.existsByUsernameIgnoreCase(
                cleanedUsername
        )) {

            throw new IllegalArgumentException(
                    "Username is already in use"
            );
        }

        user.setUsername(cleanedUsername);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Transactional
    public User updateEmail(Long id, String email) {

        User user = findById(id);

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "Email is required"
            );
        }

        String cleanedEmail =
                email.trim().toLowerCase();

        if (!user.getEmail().equalsIgnoreCase(cleanedEmail)
                && userRepository.existsByEmailIgnoreCase(
                cleanedEmail
        )) {

            throw new IllegalArgumentException(
                    "Email is already in use"
            );
        }

        user.setEmail(cleanedEmail);

        return userRepository.save(user);
    }

    @Transactional
    public User updateRole(Long id, RoleType role) {

        User user = findById(id);

        if (role == null) {
            throw new IllegalArgumentException(
                    "Role is required"
            );
        }

        user.setRole(role);

        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long id) {

        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "User not found"
            );
        }

        userRepository.deleteById(id);
    }
}
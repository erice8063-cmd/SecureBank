package org.example.dbproject.service;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.UserAccount;
import org.example.dbproject.repository.UserAccountRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;

    public List<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }
    public UserAccount findById(Long id) {
        return userAccountRepository.findById(id)
                .orElse(null);
    }
    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }
    public void deleteById(Long id) {
        userAccountRepository.deleteById(id);
    }
}

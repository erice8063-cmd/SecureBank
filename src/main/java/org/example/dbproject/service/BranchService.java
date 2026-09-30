package org.example.dbproject.service;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.Branch;
import org.example.dbproject.repository.BranchRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;

    public List<Branch> findAll() {
        return branchRepository.findAll();
    }
    public Branch findById(Long id) {
        return branchRepository.findById(id)
                .orElse(null);
    }
    public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }
    public void deleteById(Long id) {
        branchRepository.deleteById(id);
    }
}

package org.example.dbproject.service;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.Beneficiary;
import org.example.dbproject.repository.BeneficiaryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeneficiaryService {
    private final BeneficiaryRepository beneficiaryRepository;

    public List<Beneficiary> findAll() {
        return beneficiaryRepository.findAll();
    }
    public Beneficiary findById(Long id) {
        return beneficiaryRepository.findById(id)
                .orElse(null);
    }
    public Beneficiary save(Beneficiary beneficiary) {
        return beneficiaryRepository.save(beneficiary);
    }
    public void deleteById(Long id) {
        beneficiaryRepository.deleteById(id);
    }
}

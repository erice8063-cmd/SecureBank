package org.example.dbproject.repository;
import org.example.dbproject.entity.Account;
import org.example.dbproject.entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    List<Beneficiary> findByAccount(Account account);

}
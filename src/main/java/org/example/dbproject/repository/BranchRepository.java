package org.example.dbproject.repository;
import org.example.dbproject.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByIfscCode(String ifscCode);

}
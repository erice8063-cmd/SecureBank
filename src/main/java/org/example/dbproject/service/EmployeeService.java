package org.example.dbproject.service;
import lombok.RequiredArgsConstructor;
import org.example.dbproject.entity.Employee;
import org.example.dbproject.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElse(null);
    }
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
}

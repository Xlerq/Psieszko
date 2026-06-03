package edu.prz.psieszko.kindergartenstructure.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByOrderByLastNameAscFirstNameAscIdAsc();
}

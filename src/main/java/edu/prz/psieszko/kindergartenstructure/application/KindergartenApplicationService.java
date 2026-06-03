package edu.prz.psieszko.kindergartenstructure.application;

import edu.prz.psieszko.foundation.domain.NotExistsException;
import edu.prz.psieszko.kindergartenstructure.domain.Employee;
import edu.prz.psieszko.kindergartenstructure.domain.EmployeeFactory;
import edu.prz.psieszko.kindergartenstructure.domain.EmployeeRepository;
import edu.prz.psieszko.kindergartenstructure.domain.Role;
import edu.prz.psieszko.kindergartenstructure.domain.RoleFactory;
import edu.prz.psieszko.kindergartenstructure.domain.RoleRepository;
import edu.prz.psieszko.shared.identity.EmployeeId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KindergartenApplicationService {

    private final EmployeeFactory employeeFactory;
    private final RoleFactory roleFactory;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public Employee createEmployee(CreateEmployeeCommand command) {
        Employee employee = employeeFactory.create(new EmployeeFactory.Input(
                command.firstName(),
                command.lastName(),
                command.phoneNumber(),
                command.email()
        ));

        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public Employee getEmployee(EmployeeId employeeId) {
        Employee employee = getRequiredEmployee(employeeId);
        employee.getRoles().size();
        return employee;
    }

    @Transactional(readOnly = true)
    public List<Employee> listEmployees() {
        List<Employee> employees = employeeRepository.findAllByOrderByLastNameAscFirstNameAscIdAsc();
        employees.forEach(employee -> employee.getRoles().size());
        return employees;
    }

    @Transactional
    public Employee assignRole(AssignRoleCommand command) {
        Employee employee = getRequiredEmployee(command.employeeId());
        Role role = getOrCreateRole(command.roleName());

        employee.assignRole(role);
        return employeeRepository.saveAndFlush(employee);
    }

    private Employee getRequiredEmployee(EmployeeId employeeId) {
        return employeeRepository.findById(employeeId.id())
                .orElseThrow(() -> NotExistsException.of("Employee does not exist"));
    }

    private Role getOrCreateRole(String roleName) {
        String normalizedRoleName = roleName == null ? null : roleName.trim();

        return roleRepository.findByName(normalizedRoleName)
                .orElseGet(() -> roleRepository.saveAndFlush(
                        roleFactory.create(new RoleFactory.Input(normalizedRoleName))
                ));
    }

    public record CreateEmployeeCommand(
            String firstName,
            String lastName,
            String phoneNumber,
            String email
    ) {
    }

    public record AssignRoleCommand(
            EmployeeId employeeId,
            String roleName
    ) {
    }
}

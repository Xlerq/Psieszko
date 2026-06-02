package edu.prz.psieszko.kindergartenstructure.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.prz.psieszko.foundation.domain.NotExistsException;
import edu.prz.psieszko.kindergartenstructure.application.KindergartenApplicationService.AssignRoleCommand;
import edu.prz.psieszko.kindergartenstructure.application.KindergartenApplicationService.CreateEmployeeCommand;
import edu.prz.psieszko.kindergartenstructure.domain.Employee;
import edu.prz.psieszko.kindergartenstructure.domain.EmployeeRepository;
import edu.prz.psieszko.kindergartenstructure.domain.RoleRepository;
import edu.prz.psieszko.shared.identity.EmployeeId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-kindergarten-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class KindergartenApplicationServiceTest {

    @Autowired
    private KindergartenApplicationService kindergartenApplicationService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void createsAndGetsEmployeeThroughApplicationService() {
        Employee employee = kindergartenApplicationService.createEmployee(new CreateEmployeeCommand(
                "Anna",
                "Kowalska",
                "600700800",
                "anna.kowalska@example.com"
        ));

        assertNotNull(employee.getId());

        Employee savedEmployee = kindergartenApplicationService.getEmployee(new EmployeeId(employee.getId()));

        assertEquals("Anna", savedEmployee.getFirstName());
        assertEquals("Kowalska", savedEmployee.getLastName());
        assertEquals("600700800", savedEmployee.getPhoneNumber());
        assertEquals("anna.kowalska@example.com", savedEmployee.getEmail());
    }

    @Test
    void listsEmployeesOrderedByLastNameAndFirstName() {
        kindergartenApplicationService.createEmployee(new CreateEmployeeCommand(
                "Jan",
                "Nowak",
                "600700800",
                "jan.nowak@example.com"
        ));
        kindergartenApplicationService.createEmployee(new CreateEmployeeCommand(
                "Anna",
                "Kowalska",
                "500600700",
                "anna.kowalska@example.com"
        ));

        var employees = kindergartenApplicationService.listEmployees();

        assertEquals(2, employees.size());
        assertEquals("Kowalska", employees.getFirst().getLastName());
        assertEquals("Nowak", employees.getLast().getLastName());
    }

    @Test
    void assignsRoleToEmployee() {
        Employee employee = kindergartenApplicationService.createEmployee(new CreateEmployeeCommand(
                "Anna",
                "Kowalska",
                "600700800",
                "anna.kowalska@example.com"
        ));

        Employee employeeWithRole = kindergartenApplicationService.assignRole(new AssignRoleCommand(
                new EmployeeId(employee.getId()),
                "Teacher"
        ));

        assertEquals(1, employeeWithRole.getRoles().size());
        assertEquals("Teacher", employeeWithRole.getRoles().iterator().next().getName());
        assertEquals(1, roleRepository.count());
    }

    @Test
    void rejectsMissingEmployee() {
        assertThrows(NotExistsException.class, () ->
                kindergartenApplicationService.getEmployee(new EmployeeId(999L))
        );
    }
}

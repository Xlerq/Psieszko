package edu.prz.psieszko.kindergartenstructure.application;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.prz.psieszko.foundation.exception.GlobalExceptionHandler;
import edu.prz.psieszko.kindergartenstructure.domain.Employee;
import edu.prz.psieszko.kindergartenstructure.domain.EmployeeFactory;
import edu.prz.psieszko.kindergartenstructure.domain.Role;
import edu.prz.psieszko.kindergartenstructure.domain.RoleFactory;
import edu.prz.psieszko.shared.identity.EmployeeId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class KindergartenControllerTest {

    private KindergartenApplicationService kindergartenApplicationService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        kindergartenApplicationService = mock(KindergartenApplicationService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new KindergartenController(kindergartenApplicationService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createsEmployee() throws Exception {
        Employee employee = employee("Anna", "Kowalska", "600700800", "anna.kowalska@example.com");

        when(kindergartenApplicationService.createEmployee(argThat(command ->
                command.firstName().equals("Anna")
                        && command.lastName().equals("Kowalska")
                        && command.phoneNumber().equals("600700800")
                        && command.email().equals("anna.kowalska@example.com")
        ))).thenReturn(employee);

        mockMvc.perform(post("/api/kindergarten/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Anna",
                                  "lastName": "Kowalska",
                                  "phoneNumber": "600700800",
                                  "email": "anna.kowalska@example.com"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Kowalska"));

        verify(kindergartenApplicationService).createEmployee(argThat(command ->
                command.firstName().equals("Anna") && command.lastName().equals("Kowalska")
        ));
    }

    @Test
    void getsEmployee() throws Exception {
        Employee employee = employee("Anna", "Kowalska", "600700800", "anna.kowalska@example.com");

        when(kindergartenApplicationService.getEmployee(new EmployeeId(7L))).thenReturn(employee);

        mockMvc.perform(get("/api/kindergarten/employees/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.email").value("anna.kowalska@example.com"));
    }

    @Test
    void listsEmployees() throws Exception {
        when(kindergartenApplicationService.listEmployees()).thenReturn(List.of(
                employee("Anna", "Kowalska", "600700800", "anna.kowalska@example.com"),
                employee("Jan", "Nowak", "500600700", "jan.nowak@example.com")
        ));

        mockMvc.perform(get("/api/kindergarten/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("Kowalska"))
                .andExpect(jsonPath("$[1].lastName").value("Nowak"));
    }

    @Test
    void assignsRole() throws Exception {
        Employee employee = employee("Anna", "Kowalska", "600700800", "anna.kowalska@example.com");
        employee.assignRole(role("Teacher"));

        when(kindergartenApplicationService.assignRole(argThat(command ->
                command.employeeId().equals(new EmployeeId(7L)) && command.roleName().equals("Teacher")
        ))).thenReturn(employee);

        mockMvc.perform(post("/api/kindergarten/employees/7/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleName\":\"Teacher\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles[0].name").value("Teacher"));

        verify(kindergartenApplicationService).assignRole(argThat(command ->
                command.employeeId().equals(new EmployeeId(7L)) && command.roleName().equals("Teacher")
        ));
    }

    @Test
    void rejectsInvalidEmployeeRequest() throws Exception {
        mockMvc.perform(post("/api/kindergarten/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    private Employee employee(String firstName, String lastName, String phoneNumber, String email) {
        return new EmployeeFactory().create(new EmployeeFactory.Input(firstName, lastName, phoneNumber, email));
    }

    private Role role(String name) {
        return new RoleFactory().create(new RoleFactory.Input(name));
    }
}

package edu.prz.psieszko.kindergartenstructure.application;

import edu.prz.psieszko.kindergartenstructure.application.KindergartenApplicationService.AssignRoleCommand;
import edu.prz.psieszko.kindergartenstructure.application.KindergartenApplicationService.CreateEmployeeCommand;
import edu.prz.psieszko.kindergartenstructure.domain.Employee;
import edu.prz.psieszko.kindergartenstructure.domain.Role;
import edu.prz.psieszko.shared.identity.EmployeeId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/kindergarten/employees")
@Tag(name = "Kindergarten Structure")
public class KindergartenController {

    private final KindergartenApplicationService kindergartenApplicationService;

    public KindergartenController(KindergartenApplicationService kindergartenApplicationService) {
        this.kindergartenApplicationService = kindergartenApplicationService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        Employee employee = kindergartenApplicationService.createEmployee(request.toCommand());

        return ResponseEntity
                .created(URI.create("/api/kindergarten/employees/" + employee.getId()))
                .body(EmployeeResponse.from(employee));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable @Positive Long employeeId) {
        Employee employee = kindergartenApplicationService.getEmployee(new EmployeeId(employeeId));

        return ResponseEntity.ok(EmployeeResponse.from(employee));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> listEmployees() {
        List<EmployeeResponse> employees = kindergartenApplicationService.listEmployees()
                .stream()
                .map(EmployeeResponse::from)
                .toList();

        return ResponseEntity.ok(employees);
    }

    @PostMapping("/{employeeId}/roles")
    public ResponseEntity<EmployeeResponse> assignRole(
            @PathVariable @Positive Long employeeId,
            @Valid @RequestBody AssignRoleRequest request
    ) {
        Employee employee = kindergartenApplicationService.assignRole(request.toCommand(new EmployeeId(employeeId)));

        return ResponseEntity.ok(EmployeeResponse.from(employee));
    }

    public record CreateEmployeeRequest(
            @NotBlank @Size(max = 100) String firstName,
            @NotBlank @Size(max = 100) String lastName,
            @NotBlank @Size(max = 30) String phoneNumber,
            @NotBlank @Email @Size(max = 255) String email
    ) {

        CreateEmployeeCommand toCommand() {
            return new CreateEmployeeCommand(firstName, lastName, phoneNumber, email);
        }
    }

    public record AssignRoleRequest(
            @NotBlank @Size(max = 100) String roleName
    ) {

        AssignRoleCommand toCommand(EmployeeId employeeId) {
            return new AssignRoleCommand(employeeId, roleName);
        }
    }

    public record EmployeeResponse(
            Long id,
            String firstName,
            String lastName,
            String phoneNumber,
            String email,
            List<RoleResponse> roles
    ) {

        static EmployeeResponse from(Employee employee) {
            return new EmployeeResponse(
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getPhoneNumber(),
                    employee.getEmail(),
                    employee.getRoles()
                            .stream()
                            .sorted(Comparator.comparing(Role::getName))
                            .map(RoleResponse::from)
                            .toList()
            );
        }
    }

    public record RoleResponse(
            Long id,
            String name
    ) {

        static RoleResponse from(Role role) {
            return new RoleResponse(role.getId(), role.getName());
        }
    }
}

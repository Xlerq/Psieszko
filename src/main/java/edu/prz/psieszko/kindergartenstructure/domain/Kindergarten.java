package edu.prz.psieszko.kindergartenstructure.domain;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Aggregate root for the kindergarten structure bounded context.
 *
 * Associations:
 * - Employee
 * - Role
 */
@Entity
@Table(name = "kindergartens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Kindergarten extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "kindergarten_id")
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "kindergarten_id")
    private List<Role> roles = new ArrayList<>();

    Kindergarten(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Kindergarten name cannot be blank");
        }
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        employees.add(employee);
    }

    public void addRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        roles.add(role);
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public List<Role> getRoles() {
        return Collections.unmodifiableList(roles);
    }
}

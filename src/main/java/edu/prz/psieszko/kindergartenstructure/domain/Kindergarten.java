package edu.prz.psieszko.kindergartenstructure.domain;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate root for the kindergarten structure bounded context.
 *
 * Associations:
 * - Employee
 * - Role
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Kindergarten extends BaseEntity {

    String name;

    @OneToMany
    List<Employee> employees = new ArrayList<>();

    @OneToMany
    List<Role> roles = new ArrayList<>();
}
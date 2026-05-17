package edu.prz.psieszko.kindergartenstructure.domain;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import edu.prz.psieszko.shared.identity.EmployeeId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends BaseEntity {

    String firstName;

    String lastName;
}
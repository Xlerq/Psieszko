package edu.prz.psieszko.shared.identity;

import com.fasterxml.jackson.annotation.JsonValue;
import edu.prz.psieszko.foundation.domain.Identity;
import jakarta.persistence.Embeddable;

@Embeddable
public record EmployeeId(@JsonValue Long id) implements Identity {

}
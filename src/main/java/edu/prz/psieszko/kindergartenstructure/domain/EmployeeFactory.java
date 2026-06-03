package edu.prz.psieszko.kindergartenstructure.domain;

import edu.prz.psieszko.foundation.domain.StandardFactory;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFactory implements StandardFactory<EmployeeFactory.Input, Employee> {

    @Override
    public Employee create(Input input) {
        if (input == null) {
            throw new IllegalArgumentException("Employee factory input cannot be null");
        }
        return new Employee(input.firstName(), input.lastName(), input.phoneNumber(), input.email());
    }

    public record Input(
            String firstName,
            String lastName,
            String phoneNumber,
            String email
    ) {
    }
}

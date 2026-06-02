package edu.prz.psieszko.kindergartenstructure.domain;

import edu.prz.psieszko.foundation.domain.StandardFactory;
import org.springframework.stereotype.Component;

@Component
public class RoleFactory implements StandardFactory<RoleFactory.Input, Role> {

    @Override
    public Role create(Input input) {
        if (input == null) {
            throw new IllegalArgumentException("Role factory input cannot be null");
        }
        return new Role(input.name());
    }

    public record Input(String name) {
    }
}

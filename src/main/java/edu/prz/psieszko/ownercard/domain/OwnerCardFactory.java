package edu.prz.psieszko.ownercard.domain;

import edu.prz.psieszko.foundation.domain.StandardFactory;
import org.springframework.stereotype.Component;

/**
 * Factory responsible for creating OwnerCard aggregates.
 */
@Component
public class OwnerCardFactory implements StandardFactory<OwnerCardFactory.Input, OwnerCard> {

    @Override
    public OwnerCard create(Input input) {
        if (input == null) {
            throw new IllegalArgumentException("Owner card factory input cannot be null");
        }
        return new OwnerCard(new Owner(input.firstName(), input.lastName(), input.phoneNumber(), input.email()));
    }

    public record Input(
            String firstName,
            String lastName,
            String phoneNumber,
            String email
    ) {
    }
}

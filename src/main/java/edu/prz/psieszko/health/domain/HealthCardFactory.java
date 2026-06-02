package edu.prz.psieszko.health.domain;

import edu.prz.psieszko.foundation.domain.StandardFactory;
import edu.prz.psieszko.shared.identity.DogId;
import org.springframework.stereotype.Component;

@Component
public class HealthCardFactory implements StandardFactory<HealthCardFactory.Input, HealthCard> {

    @Override
    public HealthCard create(Input input) {
        if (input == null) {
            throw new IllegalArgumentException("Health card factory input cannot be null");
        }
        return new HealthCard(input.dogId());
    }

    public record Input(DogId dogId) {
    }
}

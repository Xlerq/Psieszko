package edu.prz.psieszko.shared.identity;

import com.fasterxml.jackson.annotation.JsonValue;
import edu.prz.psieszko.foundation.domain.Identity;
import jakarta.persistence.Embeddable;

@Embeddable
public record HealthCardId(@JsonValue Long id) implements Identity {

    public HealthCardId {
        if (id == null) {
            throw new IllegalArgumentException("Health card id cannot be null");
        }
    }
}

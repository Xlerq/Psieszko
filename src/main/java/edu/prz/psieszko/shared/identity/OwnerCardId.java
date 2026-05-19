package edu.prz.psieszko.shared.identity;

import com.fasterxml.jackson.annotation.JsonValue;
import edu.prz.psieszko.foundation.domain.Identity;
import jakarta.persistence.Embeddable;

@Embeddable
public record OwnerCardId(@JsonValue Long id) implements Identity {

    public OwnerCardId {
        if (id == null) {
            throw new IllegalArgumentException("Owner card id cannot be null");
        }
    }
}

package edu.prz.psieszko.shared.identity;

import com.fasterxml.jackson.annotation.JsonValue;
import edu.prz.psieszko.foundation.domain.Identity;
import jakarta.persistence.Embeddable;

@Embeddable
public record DogId(@JsonValue Long id) implements Identity {

    public DogId {
        if (id == null) {
            throw new IllegalArgumentException("Dog id cannot be null");
        }
    }
}

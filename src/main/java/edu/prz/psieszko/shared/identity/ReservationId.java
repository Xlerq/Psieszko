package edu.prz.psieszko.shared.identity;

import com.fasterxml.jackson.annotation.JsonValue;
import edu.prz.psieszko.foundation.domain.Identity;
import jakarta.persistence.Embeddable;

@Embeddable
public record ReservationId(@JsonValue Long id) implements Identity {

    public ReservationId {
        if (id == null) {
            throw new IllegalArgumentException("Reservation id cannot be null");
        }
    }
}
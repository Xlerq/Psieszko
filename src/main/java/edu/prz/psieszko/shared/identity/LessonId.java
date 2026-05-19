package edu.prz.psieszko.shared.identity;

import com.fasterxml.jackson.annotation.JsonValue;
import edu.prz.psieszko.foundation.domain.Identity;
import jakarta.persistence.Embeddable;

@Embeddable
public record LessonId(@JsonValue Long id) implements Identity {

    public LessonId {
        if (id == null) {
            throw new IllegalArgumentException("Lesson id cannot be null");
        }
    }
}

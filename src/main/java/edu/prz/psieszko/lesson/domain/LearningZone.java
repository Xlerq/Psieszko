package edu.prz.psieszko.lesson.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningZone {

    @Column(name = "learning_zone_name", nullable = false)
    private String name;

    @Column(name = "learning_zone_location")
    private String location;

    public LearningZone(String name, String location) {
        this.name = requireText(name, "Learning zone name");
        this.location = normalize(location);
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return value.trim();
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }
}

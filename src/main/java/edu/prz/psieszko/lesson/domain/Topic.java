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
public class Topic {

    @Column(name = "topic_name", nullable = false)
    private String name;

    @Column(name = "topic_description")
    private String description;

    public Topic(String name, String description) {
        this.name = requireText(name, "Topic name");
        this.description = normalize(description);
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

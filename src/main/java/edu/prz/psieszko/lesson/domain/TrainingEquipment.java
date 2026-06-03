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
public class TrainingEquipment {

    @Column(name = "equipment_name", nullable = false)
    private String name;

    public TrainingEquipment(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Training equipment name cannot be blank");
        }
        this.name = name.trim();
    }
}

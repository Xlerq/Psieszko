package edu.prz.psieszko.health.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VeterinaryVisit {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "visit_veterinarian_name", nullable = false)),
            @AttributeOverride(name = "licenseNumber", column = @Column(name = "visit_veterinarian_license_number"))
    })
    private Veterinarian veterinarian;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Column(name = "visit_description", nullable = false)
    private String description;

    @Column(name = "visit_recommendations")
    private String recommendations;

    public VeterinaryVisit(
            Veterinarian veterinarian,
            LocalDate visitDate,
            String description,
            String recommendations
    ) {
        this.veterinarian = requireVeterinarian(veterinarian);
        this.visitDate = requireDate(visitDate, "Visit date");
        this.description = requireText(description, "Visit description");
        this.recommendations = normalize(recommendations);
    }

    private Veterinarian requireVeterinarian(Veterinarian veterinarian) {
        if (veterinarian == null) {
            throw new IllegalArgumentException("Veterinarian cannot be null");
        }
        return veterinarian;
    }

    private LocalDate requireDate(LocalDate date, String fieldName) {
        if (date == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
        return date;
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

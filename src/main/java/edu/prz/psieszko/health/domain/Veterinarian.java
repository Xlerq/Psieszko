package edu.prz.psieszko.health.domain;

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
public class Veterinarian {

    @Column(name = "veterinarian_name", nullable = false)
    private String name;

    @Column(name = "veterinarian_license_number")
    private String licenseNumber;

    public Veterinarian(String name, String licenseNumber) {
        this.name = requireText(name, "Veterinarian name");
        this.licenseNumber = normalize(licenseNumber);
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

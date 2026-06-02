package edu.prz.psieszko.health.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vaccination {

    @Column(name = "vaccination_name", nullable = false)
    private String name;

    @Column(name = "vaccination_date", nullable = false)
    private LocalDate vaccinationDate;

    @Column(name = "next_vaccination_date")
    private LocalDate nextVaccinationDate;

    public Vaccination(String name, LocalDate vaccinationDate, LocalDate nextVaccinationDate) {
        this.name = requireText(name, "Vaccination name");
        this.vaccinationDate = requireDate(vaccinationDate, "Vaccination date");
        this.nextVaccinationDate = nextVaccinationDate;
        validateNextDate();
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return value.trim();
    }

    private LocalDate requireDate(LocalDate date, String fieldName) {
        if (date == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
        return date;
    }

    private void validateNextDate() {
        if (nextVaccinationDate != null && nextVaccinationDate.isBefore(vaccinationDate)) {
            throw new IllegalArgumentException("Next vaccination date cannot be before vaccination date");
        }
    }
}

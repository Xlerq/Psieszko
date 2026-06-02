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
public class Medicine {

    @Column(name = "medicine_name", nullable = false)
    private String name;

    @Column(name = "dosage", nullable = false)
    private String dosage;

    @Column(name = "medicine_start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "medicine_end_date")
    private LocalDate endDate;

    public Medicine(String name, String dosage, LocalDate startDate, LocalDate endDate) {
        this.name = requireText(name, "Medicine name");
        this.dosage = requireText(dosage, "Dosage");
        this.startDate = requireDate(startDate, "Medicine start date");
        this.endDate = endDate;
        validateDateOrder();
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

    private void validateDateOrder() {
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Medicine end date cannot be before start date");
        }
    }
}

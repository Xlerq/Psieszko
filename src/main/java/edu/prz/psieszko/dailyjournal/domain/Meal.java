package edu.prz.psieszko.dailyjournal.domain;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "daily_journal_meals")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meal extends BaseEntity {

    @Column(nullable = false)
    private LocalTime servedAt;

    @Column(nullable = false, length = 255)
    private String foodName;

    @Column(name = "amount_in_grams")
    private Integer amountInGrams;

    @Column(length = 1000)
    private String notes;

    Meal(LocalTime servedAt, String foodName, Integer amountInGrams, String notes) {
        if (servedAt == null) {
            throw new IllegalArgumentException("Meal time cannot be null");
        }
        if (foodName == null || foodName.isBlank()) {
            throw new IllegalArgumentException("Food name cannot be blank");
        }
        if (amountInGrams != null && amountInGrams <= 0) {
            throw new IllegalArgumentException("Meal amount must be positive");
        }
        this.servedAt = servedAt;
        this.foodName = foodName;
        this.amountInGrams = amountInGrams;
        this.notes = notes;
    }
}

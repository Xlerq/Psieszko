package edu.prz.psieszko.dailyjournal.domain;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import edu.prz.psieszko.shared.identity.DogId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Aggregate root for the daily journal bounded context.
 *
 * Associations:
 * - Dog
 * - Activity
 * - Incident
 * - Meal
 */
@Entity
@Table(
        name = "daily_journals",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_daily_journal_dog_date",
                columnNames = {"dog_id", "journal_date"}
        )
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyJournal extends BaseEntity {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "dog_id", nullable = false))
    private DogId dogId;

    @Column(name = "journal_date", nullable = false)
    private LocalDate journalDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "daily_journal_id", nullable = false)
    @OrderBy("occurredAt ASC, id ASC")
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "daily_journal_id", nullable = false)
    @OrderBy("occurredAt ASC, id ASC")
    private List<Incident> incidents = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "daily_journal_id", nullable = false)
    @OrderBy("servedAt ASC, id ASC")
    private List<Meal> meals = new ArrayList<>();

    DailyJournal(DogId dogId, LocalDate journalDate) {
        if (dogId == null) {
            throw new IllegalArgumentException("Dog id cannot be null");
        }
        if (journalDate == null) {
            throw new IllegalArgumentException("Journal date cannot be null");
        }
        this.dogId = dogId;
        this.journalDate = journalDate;
    }

    public Activity addActivity(LocalTime occurredAt, String description) {
        Activity activity = new Activity(occurredAt, description);
        activities.add(activity);
        return activity;
    }

    public Incident registerIncident(LocalTime occurredAt, String description, String actionTaken) {
        Incident incident = new Incident(occurredAt, description, actionTaken);
        incidents.add(incident);
        return incident;
    }

    public Meal addMeal(LocalTime servedAt, String foodName, Integer amountInGrams, String notes) {
        Meal meal = new Meal(servedAt, foodName, amountInGrams, notes);
        meals.add(meal);
        return meal;
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

    public List<Incident> getIncidents() {
        return Collections.unmodifiableList(incidents);
    }

    public List<Meal> getMeals() {
        return Collections.unmodifiableList(meals);
    }
}

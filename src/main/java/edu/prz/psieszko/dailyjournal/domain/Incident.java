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
@Table(name = "daily_journal_incidents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Incident extends BaseEntity {

    @Column(nullable = false)
    private LocalTime occurredAt;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(length = 1000)
    private String actionTaken;

    Incident(LocalTime occurredAt, String description, String actionTaken) {
        if (occurredAt == null) {
            throw new IllegalArgumentException("Incident time cannot be null");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Incident description cannot be blank");
        }
        this.occurredAt = occurredAt;
        this.description = description;
        this.actionTaken = actionTaken;
    }
}

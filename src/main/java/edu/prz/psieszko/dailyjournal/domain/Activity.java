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
@Table(name = "daily_journal_activities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity extends BaseEntity {

    @Column(nullable = false)
    private LocalTime occurredAt;

    @Column(nullable = false, length = 1000)
    private String description;

    Activity(LocalTime occurredAt, String description) {
        if (occurredAt == null) {
            throw new IllegalArgumentException("Activity time cannot be null");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Activity description cannot be blank");
        }
        this.occurredAt = occurredAt;
        this.description = description;
    }
}

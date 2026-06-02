package edu.prz.psieszko.lesson.domain;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import edu.prz.psieszko.shared.identity.EmployeeId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Aggregate root for the lesson bounded context.
 *
 * Associations:
 * - Employee
 * - Topic
 * - LearningZone
 * - TrainingEquipment
 */
@Entity
@Table(name = "lessons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson extends BaseEntity {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "employee_id", nullable = false))
    private EmployeeId employeeId;

    @Embedded
    private Topic topic;

    @Embedded
    private LearningZone learningZone;

    @ElementCollection
    @CollectionTable(name = "lesson_equipment", joinColumns = @JoinColumn(name = "lesson_id"))
    private Set<TrainingEquipment> equipment = new LinkedHashSet<>();

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    Lesson(
            EmployeeId employeeId,
            Topic topic,
            LearningZone learningZone,
            Set<TrainingEquipment> equipment,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        this.employeeId = requireEmployee(employeeId);
        this.topic = requireTopic(topic);
        this.learningZone = requireLearningZone(learningZone);
        this.startDate = requireDate(startDate, "Start date");
        this.endDate = requireDate(endDate, "End date");
        validateDateOrder(this.startDate, this.endDate);
        replaceEquipment(equipment);
    }

    public void changeTopic(Topic topic) {
        this.topic = requireTopic(topic);
    }

    public void changeLearningZone(LearningZone learningZone) {
        this.learningZone = requireLearningZone(learningZone);
    }

    public void changeEmployee(EmployeeId employeeId) {
        this.employeeId = requireEmployee(employeeId);
    }

    public void changeDate(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime newStartDate = requireDate(startDate, "Start date");
        LocalDateTime newEndDate = requireDate(endDate, "End date");
        validateDateOrder(newStartDate, newEndDate);
        this.startDate = newStartDate;
        this.endDate = newEndDate;
    }

    public void replaceEquipment(Set<TrainingEquipment> equipment) {
        this.equipment.clear();
        if (equipment == null) {
            return;
        }
        equipment.forEach(this::addEquipment);
    }

    public void addEquipment(TrainingEquipment trainingEquipment) {
        if (trainingEquipment == null) {
            throw new IllegalArgumentException("Training equipment cannot be null");
        }
        this.equipment.add(trainingEquipment);
    }

    public Set<TrainingEquipment> getEquipment() {
        return Collections.unmodifiableSet(equipment);
    }

    private EmployeeId requireEmployee(EmployeeId employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee id cannot be null");
        }
        return employeeId;
    }

    private Topic requireTopic(Topic topic) {
        if (topic == null) {
            throw new IllegalArgumentException("Topic cannot be null");
        }
        return topic;
    }

    private LearningZone requireLearningZone(LearningZone learningZone) {
        if (learningZone == null) {
            throw new IllegalArgumentException("Learning zone cannot be null");
        }
        return learningZone;
    }

    private LocalDateTime requireDate(LocalDateTime date, String fieldName) {
        if (date == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
        return date;
    }

    private void validateDateOrder(LocalDateTime startDate, LocalDateTime endDate) {
        if (!startDate.isBefore(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }
}

package edu.prz.psieszko.lesson.domain;

import edu.prz.psieszko.foundation.domain.StandardFactory;
import edu.prz.psieszko.shared.identity.EmployeeId;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class LessonFactory implements StandardFactory<LessonFactory.Input, Lesson> {

    @Override
    public Lesson create(Input input) {
        if (input == null) {
            throw new IllegalArgumentException("Lesson factory input cannot be null");
        }
        return new Lesson(
                input.employeeId(),
                input.topic(),
                input.learningZone(),
                input.equipment(),
                input.startDate(),
                input.endDate()
        );
    }

    public record Input(
            EmployeeId employeeId,
            Topic topic,
            LearningZone learningZone,
            Set<TrainingEquipment> equipment,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
    }
}

package edu.prz.psieszko.lesson.application;

import edu.prz.psieszko.foundation.domain.NotExistsException;
import edu.prz.psieszko.lesson.domain.LearningZone;
import edu.prz.psieszko.lesson.domain.Lesson;
import edu.prz.psieszko.lesson.domain.LessonFactory;
import edu.prz.psieszko.lesson.domain.LessonRepository;
import edu.prz.psieszko.lesson.domain.Topic;
import edu.prz.psieszko.lesson.domain.TrainingEquipment;
import edu.prz.psieszko.shared.identity.EmployeeId;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LessonApplicationService {

    private final LessonFactory lessonFactory;
    private final LessonRepository lessonRepository;

    @Transactional
    public Lesson createLesson(CreateLessonCommand command) {
        Lesson lesson = lessonFactory.create(new LessonFactory.Input(
                command.employeeId(),
                command.topic(),
                command.learningZone(),
                command.equipment(),
                command.startDate(),
                command.endDate()
        ));

        return lessonRepository.save(lesson);
    }

    @Transactional(readOnly = true)
    public Lesson getLesson(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> NotExistsException.of("Lesson does not exist"));
    }

    @Transactional
    public Lesson changeTopic(Long id, Topic topic) {
        Lesson lesson = getLesson(id);
        lesson.changeTopic(topic);
        return lessonRepository.save(lesson);
    }

    @Transactional
    public Lesson changeLearningZone(Long id, LearningZone learningZone) {
        Lesson lesson = getLesson(id);
        lesson.changeLearningZone(learningZone);
        return lessonRepository.save(lesson);
    }

    @Transactional
    public Lesson changeEmployee(Long id, EmployeeId employeeId) {
        Lesson lesson = getLesson(id);
        lesson.changeEmployee(employeeId);
        return lessonRepository.save(lesson);
    }

    @Transactional
    public Lesson replaceEquipment(Long id, Set<TrainingEquipment> equipment) {
        Lesson lesson = getLesson(id);
        lesson.replaceEquipment(equipment);
        return lessonRepository.save(lesson);
    }

    public record CreateLessonCommand(
            EmployeeId employeeId,
            Topic topic,
            LearningZone learningZone,
            Set<TrainingEquipment> equipment,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
    }
}

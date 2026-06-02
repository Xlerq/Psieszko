package edu.prz.psieszko.lesson.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.prz.psieszko.lesson.application.LessonApplicationService.CreateLessonCommand;
import edu.prz.psieszko.lesson.domain.LearningZone;
import edu.prz.psieszko.lesson.domain.Lesson;
import edu.prz.psieszko.lesson.domain.LessonRepository;
import edu.prz.psieszko.lesson.domain.Topic;
import edu.prz.psieszko.lesson.domain.TrainingEquipment;
import edu.prz.psieszko.shared.identity.EmployeeId;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-lesson-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class LessonApplicationServiceTest {

    @Autowired
    private LessonApplicationService lessonApplicationService;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        lessonRepository.deleteAll();
    }

    @Test
    void createsLessonThroughApplicationService() {
        Lesson lesson = lessonApplicationService.createLesson(command());

        assertNotNull(lesson.getId());
        assertEquals(new EmployeeId(7L), lesson.getEmployeeId());
        assertEquals(new Topic("Podstawowe komendy", "Siad i zostan"), lesson.getTopic());
        assertEquals(new LearningZone("Sala A", "Parter"), lesson.getLearningZone());
        assertEquals(2, lesson.getEquipment().size());
    }

    @Test
    @Transactional
    void persistsLessonWithEquipment() {
        Lesson lesson = lessonApplicationService.createLesson(command());
        entityManager.flush();
        entityManager.clear();

        Lesson savedLesson = lessonRepository.findById(lesson.getId()).orElseThrow();

        assertEquals(new EmployeeId(7L), savedLesson.getEmployeeId());
        assertEquals(Set.of(new TrainingEquipment("Kliker"), new TrainingEquipment("Mata")), savedLesson.getEquipment());
    }

    @Test
    void changesTopicLearningZoneEmployeeAndEquipment() {
        Lesson lesson = lessonApplicationService.createLesson(command());

        lessonApplicationService.changeTopic(lesson.getId(), new Topic("Socjalizacja", "Praca w grupie"));
        lessonApplicationService.changeLearningZone(lesson.getId(), new LearningZone("Plac zewnetrzny", "Ogrod"));
        lessonApplicationService.changeEmployee(lesson.getId(), new EmployeeId(8L));
        Lesson updated = lessonApplicationService.replaceEquipment(
                lesson.getId(),
                Set.of(new TrainingEquipment("Tunel"))
        );

        assertEquals(new Topic("Socjalizacja", "Praca w grupie"), updated.getTopic());
        assertEquals(new LearningZone("Plac zewnetrzny", "Ogrod"), updated.getLearningZone());
        assertEquals(new EmployeeId(8L), updated.getEmployeeId());
        assertEquals(Set.of(new TrainingEquipment("Tunel")), updated.getEquipment());
    }

    @Test
    void rejectsInvalidDateOrder() {
        CreateLessonCommand invalidCommand = new CreateLessonCommand(
                new EmployeeId(7L),
                new Topic("Podstawowe komendy", null),
                new LearningZone("Sala A", null),
                Set.of(),
                LocalDateTime.of(2030, 7, 1, 11, 0),
                LocalDateTime.of(2030, 7, 1, 10, 0)
        );

        assertThrows(IllegalArgumentException.class, () -> lessonApplicationService.createLesson(invalidCommand));
    }

    private CreateLessonCommand command() {
        return new CreateLessonCommand(
                new EmployeeId(7L),
                new Topic("Podstawowe komendy", "Siad i zostan"),
                new LearningZone("Sala A", "Parter"),
                Set.of(new TrainingEquipment("Kliker"), new TrainingEquipment("Mata")),
                LocalDateTime.of(2030, 7, 1, 10, 0),
                LocalDateTime.of(2030, 7, 1, 11, 0)
        );
    }
}

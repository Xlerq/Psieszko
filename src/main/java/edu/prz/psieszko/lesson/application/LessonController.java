package edu.prz.psieszko.lesson.application;

import edu.prz.psieszko.lesson.application.LessonApplicationService.CreateLessonCommand;
import edu.prz.psieszko.lesson.domain.LearningZone;
import edu.prz.psieszko.lesson.domain.Lesson;
import edu.prz.psieszko.lesson.domain.Topic;
import edu.prz.psieszko.lesson.domain.TrainingEquipment;
import edu.prz.psieszko.shared.identity.EmployeeId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lessons")
@Tag(name = "Lessons")
public class LessonController {

    private final LessonApplicationService lessonApplicationService;

    public LessonController(LessonApplicationService lessonApplicationService) {
        this.lessonApplicationService = lessonApplicationService;
    }

    @PostMapping
    public ResponseEntity<LessonResponse> createLesson(@Valid @RequestBody CreateLessonRequest request) {
        Lesson lesson = lessonApplicationService.createLesson(request.toCommand());

        return ResponseEntity
                .created(URI.create("/api/lessons/" + lesson.getId()))
                .body(LessonResponse.from(lesson));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonResponse> getLesson(@PathVariable Long id) {
        return ResponseEntity.ok(LessonResponse.from(lessonApplicationService.getLesson(id)));
    }

    @PatchMapping("/{id}/topic")
    public ResponseEntity<LessonResponse> changeTopic(
            @PathVariable Long id,
            @Valid @RequestBody TopicRequest request
    ) {
        return ResponseEntity.ok(LessonResponse.from(
                lessonApplicationService.changeTopic(id, request.toTopic())
        ));
    }

    @PatchMapping("/{id}/learning-zone")
    public ResponseEntity<LessonResponse> changeLearningZone(
            @PathVariable Long id,
            @Valid @RequestBody LearningZoneRequest request
    ) {
        return ResponseEntity.ok(LessonResponse.from(
                lessonApplicationService.changeLearningZone(id, request.toLearningZone())
        ));
    }

    @PatchMapping("/{id}/employee")
    public ResponseEntity<LessonResponse> changeEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request
    ) {
        return ResponseEntity.ok(LessonResponse.from(
                lessonApplicationService.changeEmployee(id, new EmployeeId(request.employeeId()))
        ));
    }

    @PatchMapping("/{id}/equipment")
    public ResponseEntity<LessonResponse> replaceEquipment(
            @PathVariable Long id,
            @Valid @RequestBody EquipmentRequest request
    ) {
        return ResponseEntity.ok(LessonResponse.from(
                lessonApplicationService.replaceEquipment(id, request.toEquipment())
        ));
    }

    public record CreateLessonRequest(
            @NotNull @Positive Long employeeId,
            @Valid @NotNull TopicRequest topic,
            @Valid @NotNull LearningZoneRequest learningZone,
            Set<@Valid EquipmentItemRequest> equipment,
            @NotNull @Future LocalDateTime startDate,
            @NotNull @Future LocalDateTime endDate
    ) {

        CreateLessonCommand toCommand() {
            return new CreateLessonCommand(
                    new EmployeeId(employeeId),
                    topic.toTopic(),
                    learningZone.toLearningZone(),
                    toEquipment(equipment),
                    startDate,
                    endDate
            );
        }
    }

    public record TopicRequest(
            @NotBlank String name,
            String description
    ) {

        Topic toTopic() {
            return new Topic(name, description);
        }
    }

    public record LearningZoneRequest(
            @NotBlank String name,
            String location
    ) {

        LearningZone toLearningZone() {
            return new LearningZone(name, location);
        }
    }

    public record EquipmentRequest(
            Set<@Valid EquipmentItemRequest> equipment
    ) {

        Set<TrainingEquipment> toEquipment() {
            return LessonController.toEquipment(equipment);
        }
    }

    public record EquipmentItemRequest(@NotBlank String name) {

        TrainingEquipment toTrainingEquipment() {
            return new TrainingEquipment(name);
        }
    }

    public record EmployeeRequest(@NotNull @Positive Long employeeId) {
    }

    public record LessonResponse(
            Long id,
            Long employeeId,
            TopicResponse topic,
            LearningZoneResponse learningZone,
            Set<EquipmentResponse> equipment,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {

        static LessonResponse from(Lesson lesson) {
            return new LessonResponse(
                    lesson.getId(),
                    lesson.getEmployeeId().id(),
                    TopicResponse.from(lesson.getTopic()),
                    LearningZoneResponse.from(lesson.getLearningZone()),
                    lesson.getEquipment()
                            .stream()
                            .map(EquipmentResponse::from)
                            .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new)),
                    lesson.getStartDate(),
                    lesson.getEndDate()
            );
        }
    }

    public record TopicResponse(String name, String description) {

        static TopicResponse from(Topic topic) {
            return new TopicResponse(topic.getName(), topic.getDescription());
        }
    }

    public record LearningZoneResponse(String name, String location) {

        static LearningZoneResponse from(LearningZone learningZone) {
            return new LearningZoneResponse(learningZone.getName(), learningZone.getLocation());
        }
    }

    public record EquipmentResponse(String name) {

        static EquipmentResponse from(TrainingEquipment trainingEquipment) {
            return new EquipmentResponse(trainingEquipment.getName());
        }
    }

    private static Set<TrainingEquipment> toEquipment(Set<EquipmentItemRequest> requestEquipment) {
        if (requestEquipment == null) {
            return Set.of();
        }
        return requestEquipment.stream()
                .map(EquipmentItemRequest::toTrainingEquipment)
                .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));
    }
}

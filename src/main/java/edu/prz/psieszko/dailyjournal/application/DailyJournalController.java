package edu.prz.psieszko.dailyjournal.application;

import edu.prz.psieszko.dailyjournal.application.DailyJournalApplicationService.CreateDailyJournalCommand;
import edu.prz.psieszko.dailyjournal.application.DailyJournalApplicationService.RecordActivityCommand;
import edu.prz.psieszko.dailyjournal.application.DailyJournalApplicationService.RecordMealCommand;
import edu.prz.psieszko.dailyjournal.application.DailyJournalApplicationService.RegisterIncidentCommand;
import edu.prz.psieszko.dailyjournal.domain.Activity;
import edu.prz.psieszko.dailyjournal.domain.DailyJournal;
import edu.prz.psieszko.dailyjournal.domain.Incident;
import edu.prz.psieszko.dailyjournal.domain.Meal;
import edu.prz.psieszko.shared.identity.DogId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/dogs/{dogId}/daily-journals")
@Tag(name = "Daily Journals")
public class DailyJournalController {

    private final DailyJournalApplicationService dailyJournalApplicationService;

    public DailyJournalController(DailyJournalApplicationService dailyJournalApplicationService) {
        this.dailyJournalApplicationService = dailyJournalApplicationService;
    }

    @PostMapping
    public ResponseEntity<DailyJournalResponse> createDailyJournal(
            @PathVariable @Positive Long dogId,
            @Valid @RequestBody CreateDailyJournalRequest request
    ) {
        DailyJournal dailyJournal = dailyJournalApplicationService.createDailyJournal(
                request.toCommand(new DogId(dogId))
        );

        return ResponseEntity
                .created(location(dogId, dailyJournal.getJournalDate()))
                .body(DailyJournalResponse.from(dailyJournal));
    }

    @PostMapping("/{journalDate}/activities")
    public ResponseEntity<ActivityResponse> recordActivity(
            @PathVariable @Positive Long dogId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journalDate,
            @Valid @RequestBody RecordActivityRequest request
    ) {
        Activity activity = dailyJournalApplicationService.recordActivity(
                request.toCommand(new DogId(dogId), journalDate)
        );

        return ResponseEntity.created(location(dogId, journalDate)).body(ActivityResponse.from(activity));
    }

    @PostMapping("/{journalDate}/incidents")
    public ResponseEntity<IncidentResponse> registerIncident(
            @PathVariable @Positive Long dogId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journalDate,
            @Valid @RequestBody RegisterIncidentRequest request
    ) {
        Incident incident = dailyJournalApplicationService.registerIncident(
                request.toCommand(new DogId(dogId), journalDate)
        );

        return ResponseEntity.created(location(dogId, journalDate)).body(IncidentResponse.from(incident));
    }

    @PostMapping("/{journalDate}/meals")
    public ResponseEntity<MealResponse> recordMeal(
            @PathVariable @Positive Long dogId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journalDate,
            @Valid @RequestBody RecordMealRequest request
    ) {
        Meal meal = dailyJournalApplicationService.recordMeal(
                request.toCommand(new DogId(dogId), journalDate)
        );

        return ResponseEntity.created(location(dogId, journalDate)).body(MealResponse.from(meal));
    }

    @GetMapping("/{journalDate}")
    public ResponseEntity<DailyJournalResponse> previewDogDailyJournal(
            @PathVariable @Positive Long dogId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journalDate
    ) {
        DailyJournal dailyJournal = dailyJournalApplicationService.previewDogDailyJournal(
                new DogId(dogId),
                journalDate
        );

        return ResponseEntity.ok(DailyJournalResponse.from(dailyJournal));
    }

    private static URI location(Long dogId, LocalDate journalDate) {
        return URI.create("/api/dogs/" + dogId + "/daily-journals/" + journalDate);
    }

    public record CreateDailyJournalRequest(
            @NotNull LocalDate journalDate
    ) {

        CreateDailyJournalCommand toCommand(DogId dogId) {
            return new CreateDailyJournalCommand(dogId, journalDate);
        }
    }

    public record RecordActivityRequest(
            @NotNull LocalTime occurredAt,
            @NotBlank @Size(max = 1000) String description
    ) {

        RecordActivityCommand toCommand(DogId dogId, LocalDate journalDate) {
            return new RecordActivityCommand(dogId, journalDate, occurredAt, description);
        }
    }

    public record RegisterIncidentRequest(
            @NotNull LocalTime occurredAt,
            @NotBlank @Size(max = 1000) String description,
            @Size(max = 1000) String actionTaken
    ) {

        RegisterIncidentCommand toCommand(DogId dogId, LocalDate journalDate) {
            return new RegisterIncidentCommand(dogId, journalDate, occurredAt, description, actionTaken);
        }
    }

    public record RecordMealRequest(
            @NotNull LocalTime servedAt,
            @NotBlank @Size(max = 255) String foodName,
            @Positive Integer amountInGrams,
            @Size(max = 1000) String notes
    ) {

        RecordMealCommand toCommand(DogId dogId, LocalDate journalDate) {
            return new RecordMealCommand(dogId, journalDate, servedAt, foodName, amountInGrams, notes);
        }
    }

    public record DailyJournalResponse(
            Long id,
            Long dogId,
            LocalDate journalDate,
            List<ActivityResponse> activities,
            List<IncidentResponse> incidents,
            List<MealResponse> meals
    ) {

        static DailyJournalResponse from(DailyJournal dailyJournal) {
            return new DailyJournalResponse(
                    dailyJournal.getId(),
                    dailyJournal.getDogId().id(),
                    dailyJournal.getJournalDate(),
                    dailyJournal.getActivities().stream().map(ActivityResponse::from).toList(),
                    dailyJournal.getIncidents().stream().map(IncidentResponse::from).toList(),
                    dailyJournal.getMeals().stream().map(MealResponse::from).toList()
            );
        }
    }

    public record ActivityResponse(
            Long id,
            LocalTime occurredAt,
            String description
    ) {

        static ActivityResponse from(Activity activity) {
            return new ActivityResponse(activity.getId(), activity.getOccurredAt(), activity.getDescription());
        }
    }

    public record IncidentResponse(
            Long id,
            LocalTime occurredAt,
            String description,
            String actionTaken
    ) {

        static IncidentResponse from(Incident incident) {
            return new IncidentResponse(
                    incident.getId(),
                    incident.getOccurredAt(),
                    incident.getDescription(),
                    incident.getActionTaken()
            );
        }
    }

    public record MealResponse(
            Long id,
            LocalTime servedAt,
            String foodName,
            Integer amountInGrams,
            String notes
    ) {

        static MealResponse from(Meal meal) {
            return new MealResponse(
                    meal.getId(),
                    meal.getServedAt(),
                    meal.getFoodName(),
                    meal.getAmountInGrams(),
                    meal.getNotes()
            );
        }
    }
}

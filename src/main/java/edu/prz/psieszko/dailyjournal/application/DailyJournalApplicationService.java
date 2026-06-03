package edu.prz.psieszko.dailyjournal.application;

import edu.prz.psieszko.dailyjournal.domain.Activity;
import edu.prz.psieszko.dailyjournal.domain.DailyJournal;
import edu.prz.psieszko.dailyjournal.domain.DailyJournalException;
import edu.prz.psieszko.dailyjournal.domain.DailyJournalFactory;
import edu.prz.psieszko.dailyjournal.domain.DailyJournalRepository;
import edu.prz.psieszko.dailyjournal.domain.Incident;
import edu.prz.psieszko.dailyjournal.domain.Meal;
import edu.prz.psieszko.dogs.domain.dog.DogRepository;
import edu.prz.psieszko.foundation.domain.NotExistsException;
import edu.prz.psieszko.shared.identity.DogId;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DailyJournalApplicationService {

    private final DailyJournalFactory dailyJournalFactory;
    private final DailyJournalRepository dailyJournalRepository;
    private final DogRepository dogRepository;

    @Transactional
    public DailyJournal createDailyJournal(CreateDailyJournalCommand command) {
        ensureDogExists(command.dogId());

        if (dailyJournalRepository.existsForDogOnDate(command.dogId(), command.journalDate())) {
            throw DailyJournalException.alreadyExists(command.dogId(), command.journalDate());
        }

        DailyJournal dailyJournal = dailyJournalFactory.create(command.dogId(), command.journalDate());
        return dailyJournalRepository.saveAndFlush(dailyJournal);
    }

    @Transactional
    public Activity recordActivity(RecordActivityCommand command) {
        DailyJournal dailyJournal = getRequiredJournal(command.dogId(), command.journalDate());
        dailyJournal.addActivity(command.occurredAt(), command.description());
        DailyJournal savedDailyJournal = dailyJournalRepository.saveAndFlush(dailyJournal);
        return savedDailyJournal.getActivities().getLast();
    }

    @Transactional
    public Incident registerIncident(RegisterIncidentCommand command) {
        DailyJournal dailyJournal = getRequiredJournal(command.dogId(), command.journalDate());
        dailyJournal.registerIncident(
                command.occurredAt(),
                command.description(),
                command.actionTaken()
        );
        DailyJournal savedDailyJournal = dailyJournalRepository.saveAndFlush(dailyJournal);
        return savedDailyJournal.getIncidents().getLast();
    }

    @Transactional
    public Meal recordMeal(RecordMealCommand command) {
        DailyJournal dailyJournal = getRequiredJournal(command.dogId(), command.journalDate());
        dailyJournal.addMeal(
                command.servedAt(),
                command.foodName(),
                command.amountInGrams(),
                command.notes()
        );
        DailyJournal savedDailyJournal = dailyJournalRepository.saveAndFlush(dailyJournal);
        return savedDailyJournal.getMeals().getLast();
    }

    @Transactional(readOnly = true)
    public DailyJournal previewDogDailyJournal(DogId dogId, LocalDate journalDate) {
        DailyJournal dailyJournal = getRequiredJournal(dogId, journalDate);
        dailyJournal.getActivities().size();
        dailyJournal.getIncidents().size();
        dailyJournal.getMeals().size();
        return dailyJournal;
    }

    private DailyJournal getRequiredJournal(DogId dogId, LocalDate journalDate) {
        ensureDogExists(dogId);
        return dailyJournalRepository.findForDogOnDate(dogId, journalDate)
                .orElseThrow(() -> NotExistsException.of("Daily journal does not exist"));
    }

    private void ensureDogExists(DogId dogId) {
        if (!dogRepository.existsById(dogId.id())) {
            throw NotExistsException.of("Dog does not exist");
        }
    }

    public record CreateDailyJournalCommand(
            DogId dogId,
            LocalDate journalDate
    ) {
    }

    public record RecordActivityCommand(
            DogId dogId,
            LocalDate journalDate,
            LocalTime occurredAt,
            String description
    ) {
    }

    public record RegisterIncidentCommand(
            DogId dogId,
            LocalDate journalDate,
            LocalTime occurredAt,
            String description,
            String actionTaken
    ) {
    }

    public record RecordMealCommand(
            DogId dogId,
            LocalDate journalDate,
            LocalTime servedAt,
            String foodName,
            Integer amountInGrams,
            String notes
    ) {
    }
}

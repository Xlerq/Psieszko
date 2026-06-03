package edu.prz.psieszko.dailyjournal.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.prz.psieszko.dailyjournal.application.DailyJournalApplicationService.CreateDailyJournalCommand;
import edu.prz.psieszko.dailyjournal.application.DailyJournalApplicationService.RecordActivityCommand;
import edu.prz.psieszko.dailyjournal.application.DailyJournalApplicationService.RecordMealCommand;
import edu.prz.psieszko.dailyjournal.application.DailyJournalApplicationService.RegisterIncidentCommand;
import edu.prz.psieszko.dailyjournal.domain.Activity;
import edu.prz.psieszko.dailyjournal.domain.DailyJournal;
import edu.prz.psieszko.dailyjournal.domain.DailyJournalException;
import edu.prz.psieszko.dailyjournal.domain.DailyJournalRepository;
import edu.prz.psieszko.dailyjournal.domain.Incident;
import edu.prz.psieszko.dailyjournal.domain.Meal;
import edu.prz.psieszko.dogs.domain.dog.AnimalTrait;
import edu.prz.psieszko.dogs.domain.dog.BehavioralProfile;
import edu.prz.psieszko.dogs.domain.dog.Breed;
import edu.prz.psieszko.dogs.domain.dog.Diet;
import edu.prz.psieszko.dogs.domain.dog.Dog;
import edu.prz.psieszko.dogs.domain.dog.DogRepository;
import edu.prz.psieszko.foundation.domain.NotExistsException;
import edu.prz.psieszko.shared.identity.DogId;
import edu.prz.psieszko.shared.identity.OwnerCardId;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-daily-journal-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class DailyJournalApplicationServiceTest {

    @Autowired
    private DailyJournalApplicationService dailyJournalApplicationService;

    @Autowired
    private DailyJournalRepository dailyJournalRepository;

    @Autowired
    private DogRepository dogRepository;

    @BeforeEach
    void setUp() {
        dailyJournalRepository.deleteAll();
        dogRepository.deleteAll();
    }

    @Test
    void keepsDailyJournalForDogAndReturnsPreview() {
        Dog dog = saveDog();
        DogId dogId = new DogId(dog.getId());
        LocalDate journalDate = LocalDate.of(2026, 6, 2);

        DailyJournal dailyJournal = dailyJournalApplicationService.createDailyJournal(
                new CreateDailyJournalCommand(dogId, journalDate)
        );
        Activity activity = dailyJournalApplicationService.recordActivity(new RecordActivityCommand(
                dogId,
                journalDate,
                LocalTime.of(9, 15),
                "Morning walk and social play"
        ));
        Incident incident = dailyJournalApplicationService.registerIncident(new RegisterIncidentCommand(
                dogId,
                journalDate,
                LocalTime.of(11, 30),
                "Dog became nervous during group play",
                "Moved dog to a calmer zone"
        ));
        Meal meal = dailyJournalApplicationService.recordMeal(new RecordMealCommand(
                dogId,
                journalDate,
                LocalTime.of(13, 0),
                "Dry food",
                180,
                "Ate full portion"
        ));

        DailyJournal preview = dailyJournalApplicationService.previewDogDailyJournal(dogId, journalDate);

        assertNotNull(dailyJournal.getId());
        assertNotNull(activity.getId());
        assertNotNull(incident.getId());
        assertNotNull(meal.getId());
        assertEquals(dogId, preview.getDogId());
        assertEquals(journalDate, preview.getJournalDate());
        assertEquals(1, preview.getActivities().size());
        assertEquals("Morning walk and social play", preview.getActivities().getFirst().getDescription());
        assertEquals(1, preview.getIncidents().size());
        assertEquals("Moved dog to a calmer zone", preview.getIncidents().getFirst().getActionTaken());
        assertEquals(1, preview.getMeals().size());
        assertEquals("Dry food", preview.getMeals().getFirst().getFoodName());
    }

    @Test
    void rejectsDuplicateDailyJournalForDogAndDate() {
        Dog dog = saveDog();
        DogId dogId = new DogId(dog.getId());
        LocalDate journalDate = LocalDate.of(2026, 6, 2);

        dailyJournalApplicationService.createDailyJournal(new CreateDailyJournalCommand(dogId, journalDate));

        assertThrows(DailyJournalException.class, () ->
                dailyJournalApplicationService.createDailyJournal(new CreateDailyJournalCommand(dogId, journalDate))
        );
    }

    @Test
    void rejectsDailyJournalForMissingDog() {
        assertThrows(NotExistsException.class, () ->
                dailyJournalApplicationService.createDailyJournal(new CreateDailyJournalCommand(
                        new DogId(999L),
                        LocalDate.of(2026, 6, 2)
                ))
        );
    }

    private Dog saveDog() {
        BehavioralProfile profile = new BehavioralProfile();
        profile.setSociable(true);
        profile.setTrained(true);

        Dog dog = new Dog();
        dog.setName("Burek");
        dog.setBreed(Breed.LABRADOR);
        dog.setDiet(Diet.STANDARD);
        dog.setBehavioralProfile(profile);
        dog.setAnimalTrait(AnimalTrait.FRIENDLY);
        dog.setOwnerCardId(new OwnerCardId(1L));
        return dogRepository.saveAndFlush(dog);
    }
}

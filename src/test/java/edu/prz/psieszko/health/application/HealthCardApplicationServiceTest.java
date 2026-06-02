package edu.prz.psieszko.health.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.prz.psieszko.health.application.HealthCardApplicationService.CreateHealthCardCommand;
import edu.prz.psieszko.health.domain.HealthCard;
import edu.prz.psieszko.health.domain.HealthCardRepository;
import edu.prz.psieszko.health.domain.Medicine;
import edu.prz.psieszko.health.domain.Vaccination;
import edu.prz.psieszko.health.domain.Veterinarian;
import edu.prz.psieszko.health.domain.VeterinaryVisit;
import edu.prz.psieszko.shared.identity.DogId;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-health-card-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class HealthCardApplicationServiceTest {

    @Autowired
    private HealthCardApplicationService healthCardApplicationService;

    @Autowired
    private HealthCardRepository healthCardRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        healthCardRepository.deleteAll();
    }

    @Test
    void createsHealthCardThroughApplicationService() {
        HealthCard healthCard = healthCardApplicationService.createHealthCard(
                new CreateHealthCardCommand(new DogId(12L))
        );

        assertNotNull(healthCard.getId());
        assertEquals(new DogId(12L), healthCard.getDogId());
    }

    @Test
    @Transactional
    void persistsMedicalHistory() {
        HealthCard healthCard = healthCardApplicationService.createHealthCard(
                new CreateHealthCardCommand(new DogId(12L))
        );

        healthCardApplicationService.registerVeterinaryVisit(
                healthCard.getId(),
                new VeterinaryVisit(
                        new Veterinarian("Anna Wet", "PW-123"),
                        LocalDate.of(2030, 1, 10),
                        "Kontrola po szczepieniu",
                        "Obserwowac apetyt"
                )
        );
        healthCardApplicationService.replaceVaccinations(
                healthCard.getId(),
                Set.of(new Vaccination("Wscieklizna", LocalDate.of(2030, 1, 10), LocalDate.of(2031, 1, 10)))
        );
        healthCardApplicationService.replaceMedicines(
                healthCard.getId(),
                Set.of(new Medicine("Probiotyk", "1 tabletka dziennie", LocalDate.of(2030, 1, 10), LocalDate.of(2030, 1, 17)))
        );

        entityManager.flush();
        entityManager.clear();

        HealthCard savedHealthCard = healthCardRepository.findById(healthCard.getId()).orElseThrow();

        assertEquals(1, savedHealthCard.getVeterinaryVisits().size());
        assertEquals(1, savedHealthCard.getVaccinations().size());
        assertEquals(1, savedHealthCard.getMedicines().size());
    }

    @Test
    void findsHealthCardByDogId() {
        HealthCard healthCard = healthCardApplicationService.createHealthCard(
                new CreateHealthCardCommand(new DogId(12L))
        );

        HealthCard foundHealthCard = healthCardApplicationService.getHealthCardByDogId(new DogId(12L));

        assertEquals(healthCard.getId(), foundHealthCard.getId());
    }

    @Test
    void rejectsInvalidVaccinationDateOrder() {
        assertThrows(IllegalArgumentException.class, () ->
                new Vaccination("Wscieklizna", LocalDate.of(2030, 1, 10), LocalDate.of(2029, 1, 10))
        );
    }

    @Test
    void rejectsInvalidMedicineDateOrder() {
        assertThrows(IllegalArgumentException.class, () ->
                new Medicine("Probiotyk", "1 tabletka dziennie", LocalDate.of(2030, 1, 17), LocalDate.of(2030, 1, 10))
        );
    }
}

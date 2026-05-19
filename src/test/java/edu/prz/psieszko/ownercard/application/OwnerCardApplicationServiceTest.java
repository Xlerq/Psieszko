package edu.prz.psieszko.ownercard.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.prz.psieszko.ownercard.application.OwnerCardApplicationService.CreateOwnerCardCommand;
import edu.prz.psieszko.ownercard.domain.OwnerCard;
import edu.prz.psieszko.ownercard.domain.OwnerCardRepository;
import edu.prz.psieszko.shared.identity.DogId;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-owner-card-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class OwnerCardApplicationServiceTest {

    @Autowired
    private OwnerCardApplicationService ownerCardApplicationService;

    @Autowired
    private OwnerCardRepository ownerCardRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        ownerCardRepository.deleteAll();
    }

    @Test
    void createsOwnerCardThroughApplicationService() {
        OwnerCard ownerCard = ownerCardApplicationService.createOwnerCard(new CreateOwnerCardCommand(
                "Jan",
                "Nowak",
                "600700800",
                "jan.nowak@example.com"
        ));

        assertNotNull(ownerCard.getId());
        assertNotNull(ownerCard.getOwner().getId());

        OwnerCard savedOwnerCard = ownerCardRepository.findById(ownerCard.getId()).orElseThrow();

        assertEquals("Jan", savedOwnerCard.getOwner().getFirstName());
        assertEquals("Nowak", savedOwnerCard.getOwner().getLastName());
        assertEquals("600700800", savedOwnerCard.getOwner().getPhoneNumber());
        assertEquals("jan.nowak@example.com", savedOwnerCard.getOwner().getEmail());
    }

    @Test
    @Transactional
    void persistsDogReferencesAsSharedIdentityValues() {
        OwnerCard ownerCard = ownerCardApplicationService.createOwnerCard(new CreateOwnerCardCommand(
                "Jan",
                "Nowak",
                "600700800",
                "jan.nowak@example.com"
        ));

        ownerCard.addDog(new DogId(44L));
        ownerCardRepository.saveAndFlush(ownerCard);
        entityManager.clear();

        OwnerCard savedOwnerCard = ownerCardRepository.findById(ownerCard.getId()).orElseThrow();

        assertEquals(1, savedOwnerCard.getDogIds().size());
        assertEquals(new DogId(44L), savedOwnerCard.getDogIds().iterator().next());
    }
}

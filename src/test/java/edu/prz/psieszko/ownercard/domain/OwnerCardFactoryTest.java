package edu.prz.psieszko.ownercard.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.prz.psieszko.shared.identity.DogId;
import org.junit.jupiter.api.Test;

class OwnerCardFactoryTest {

    private final OwnerCardFactory ownerCardFactory = new OwnerCardFactory();

    @Test
    void createsOwnerCardWithOwner() {
        OwnerCard ownerCard = ownerCardFactory.create(
                "Anna",
                "Kowalska",
                "500600700",
                "anna.kowalska@example.com"
        );

        assertEquals("Anna", ownerCard.getOwner().getFirstName());
        assertEquals("Kowalska", ownerCard.getOwner().getLastName());
        assertEquals("500600700", ownerCard.getOwner().getPhoneNumber());
        assertEquals("anna.kowalska@example.com", ownerCard.getOwner().getEmail());
        assertTrue(ownerCard.getDogIds().isEmpty());
    }

    @Test
    void storesDogReferencesAsSharedIdentities() {
        OwnerCard ownerCard = ownerCardFactory.create(
                "Anna",
                "Kowalska",
                "500600700",
                "anna.kowalska@example.com"
        );

        ownerCard.addDog(new DogId(12L));

        assertTrue(ownerCard.getDogIds().contains(new DogId(12L)));
    }
}

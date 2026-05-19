package edu.prz.psieszko.dogs.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.prz.psieszko.dogs.domain.dog.AnimalTrait;
import edu.prz.psieszko.dogs.domain.dog.BehavioralProfile;
import edu.prz.psieszko.dogs.domain.dog.Breed;
import edu.prz.psieszko.dogs.domain.dog.Diet;
import edu.prz.psieszko.dogs.domain.dog.Dog;
import edu.prz.psieszko.dogs.domain.dog.DogRepository;
import edu.prz.psieszko.shared.identity.OwnerCardId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-dog-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class DogApplicationServiceTest {

    @Autowired
    private DogApplicationService dogApplicationService;

    @Autowired
    private DogRepository dogRepository;

    @BeforeEach
    void setUp() {
        dogRepository.deleteAll();
    }

    @Test
    void registersDogThroughApplicationService() {
        BehavioralProfile profile = new BehavioralProfile();
        profile.setSociable(true);
        profile.setTrained(true);

        Dog dog = dogApplicationService.registerDog(
                "Burek",
                Breed.LABRADOR,
                Diet.STANDARD,
                profile,
                AnimalTrait.FRIENDLY,
                new OwnerCardId(1L)
        );

        assertNotNull(dog.getId());

        Dog savedDog = dogRepository.findById(dog.getId()).orElseThrow();

        assertEquals("Burek", savedDog.getName());
        assertEquals(Breed.LABRADOR, savedDog.getBreed());
        assertEquals(Diet.STANDARD, savedDog.getDiet());
        assertEquals(AnimalTrait.FRIENDLY, savedDog.getAnimalTrait());
        assertEquals(new OwnerCardId(1L), savedDog.getOwnerCardId());
        assertNotNull(savedDog.getBehavioralProfile());
    }
}
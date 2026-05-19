package edu.prz.psieszko.dogs.domain.dog;

import edu.prz.psieszko.shared.identity.OwnerCardId;
import org.springframework.stereotype.Component;

@Component
public class DogFactoryImpl implements DogFactory {

    @Override
    public Dog create(String name, Breed breed, Diet diet,
                      BehavioralProfile behavioralProfile,
                      AnimalTrait animalTrait,
                      OwnerCardId ownerCardId) {
        Dog dog = new Dog();
        dog.setName(name);
        dog.setBreed(breed);
        dog.setDiet(diet);
        dog.setBehavioralProfile(behavioralProfile);
        dog.setAnimalTrait(animalTrait);
        dog.setOwnerCardId(ownerCardId);
        return dog;
    }
}
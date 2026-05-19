package edu.prz.psieszko.dogs.domain.dog;

import edu.prz.psieszko.shared.identity.OwnerCardId;

public interface DogFactory {
    Dog create(String name, Breed breed, Diet diet,
               BehavioralProfile behavioralProfile,
               AnimalTrait animalTrait,
               OwnerCardId ownerCardId);
}
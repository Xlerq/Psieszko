package edu.prz.psieszko.dogs.application;

import edu.prz.psieszko.dogs.domain.dog.*;
import edu.prz.psieszko.shared.identity.OwnerCardId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DogApplicationService {

    private final DogFactory dogFactory;
    private final DogRepository dogRepository;

    @Transactional
    public Dog registerDog(String name, Breed breed, Diet diet,
                           BehavioralProfile behavioralProfile,
                           AnimalTrait animalTrait,
                           OwnerCardId ownerCardId) {
        Dog dog = dogFactory.create(name, breed, diet,
                                    behavioralProfile,
                                    animalTrait,
                                    ownerCardId);
        return dogRepository.save(dog);
    }

    @Transactional(readOnly = true)
    public Dog getDog(Long dogId) {
        return dogRepository.findById(dogId)
                .orElseThrow(() -> new java.util.NoSuchElementException("Dog does not exist"));
    }

    @Transactional
    public Dog changeDiet(Long dogId, Diet diet) {
        Dog dog = getDog(dogId);
        dog.changeDiet(diet);

        return dogRepository.save(dog);
    }

    @Transactional
    public Dog changeBehavioralProfile(Long dogId, BehavioralProfile behavioralProfile) {
        Dog dog = getDog(dogId);
        dog.changeBehavioralProfile(behavioralProfile);

        return dogRepository.save(dog);
    }
}

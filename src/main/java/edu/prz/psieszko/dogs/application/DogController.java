package edu.prz.psieszko.dogs.application;

import edu.prz.psieszko.dogs.domain.dog.AnimalTrait;
import edu.prz.psieszko.dogs.domain.dog.BehavioralProfile;
import edu.prz.psieszko.dogs.domain.dog.Breed;
import edu.prz.psieszko.dogs.domain.dog.Diet;
import edu.prz.psieszko.dogs.domain.dog.Dog;
import edu.prz.psieszko.shared.identity.OwnerCardId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/dogs")
@Tag(name = "Dogs")
public class DogController {

    private final DogApplicationService dogApplicationService;

    public DogController(DogApplicationService dogApplicationService) {
        this.dogApplicationService = dogApplicationService;
    }

    @PostMapping
    public ResponseEntity<DogResponse> registerDog(@Valid @RequestBody RegisterDogRequest request) {
        Dog dog = dogApplicationService.registerDog(
                request.name(),
                request.breed(),
                request.diet(),
                request.behavioralProfile().toBehavioralProfile(),
                request.animalTrait(),
                new OwnerCardId(request.ownerCardId())
        );

        return ResponseEntity
                .created(URI.create("/api/dogs/" + dog.getId()))
                .body(DogResponse.from(dog));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DogResponse> getDog(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(DogResponse.from(dogApplicationService.getDog(id)));
    }

    @PatchMapping("/{id}/diet")
    public ResponseEntity<DogResponse> changeDiet(
            @PathVariable @Positive Long id,
            @Valid @RequestBody ChangeDietRequest request
    ) {
        return ResponseEntity.ok(DogResponse.from(
                dogApplicationService.changeDiet(id, request.diet())
        ));
    }

    @PatchMapping("/{id}/behavioral-profile")
    public ResponseEntity<DogResponse> changeBehavioralProfile(
            @PathVariable @Positive Long id,
            @Valid @RequestBody BehavioralProfileRequest request
    ) {
        return ResponseEntity.ok(DogResponse.from(
                dogApplicationService.changeBehavioralProfile(id, request.toBehavioralProfile())
        ));
    }

    public record RegisterDogRequest(
            @NotBlank @Size(max = 100) String name,
            @NotNull Breed breed,
            @NotNull Diet diet,
            @Valid @NotNull BehavioralProfileRequest behavioralProfile,
            @NotNull AnimalTrait animalTrait,
            @NotNull @Positive Long ownerCardId
    ) {
    }

    public record ChangeDietRequest(@NotNull Diet diet) {
    }

    public record BehavioralProfileRequest(
            @NotNull Boolean aggressive,
            @NotNull Boolean sociable,
            @NotNull Boolean trained
    ) {

        BehavioralProfile toBehavioralProfile() {
            BehavioralProfile behavioralProfile = new BehavioralProfile();
            behavioralProfile.setAggressive(aggressive);
            behavioralProfile.setSociable(sociable);
            behavioralProfile.setTrained(trained);
            return behavioralProfile;
        }
    }

    public record DogResponse(
            Long id,
            String name,
            Breed breed,
            Diet diet,
            BehavioralProfileResponse behavioralProfile,
            AnimalTrait animalTrait,
            Long ownerCardId
    ) {

        static DogResponse from(Dog dog) {
            return new DogResponse(
                    dog.getId(),
                    dog.getName(),
                    dog.getBreed(),
                    dog.getDiet(),
                    BehavioralProfileResponse.from(dog.getBehavioralProfile()),
                    dog.getAnimalTrait(),
                    dog.getOwnerCardId().id()
            );
        }
    }

    public record BehavioralProfileResponse(
            boolean aggressive,
            boolean sociable,
            boolean trained
    ) {

        static BehavioralProfileResponse from(BehavioralProfile behavioralProfile) {
            return new BehavioralProfileResponse(
                    behavioralProfile.isAggressive(),
                    behavioralProfile.isSociable(),
                    behavioralProfile.isTrained()
            );
        }
    }
}

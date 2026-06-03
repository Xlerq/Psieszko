package edu.prz.psieszko.ownercard.application;

import edu.prz.psieszko.ownercard.application.OwnerCardApplicationService.CreateOwnerCardCommand;
import edu.prz.psieszko.ownercard.application.OwnerCardApplicationService.UpdateOwnerCardCommand;
import edu.prz.psieszko.ownercard.domain.Owner;
import edu.prz.psieszko.ownercard.domain.OwnerCard;
import edu.prz.psieszko.shared.identity.DogId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.net.URI;
import java.util.Set;
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
@RequestMapping("/api/owner-cards")
@Tag(name = "Owner Cards")
public class OwnerCardController {

    private final OwnerCardApplicationService ownerCardApplicationService;

    public OwnerCardController(OwnerCardApplicationService ownerCardApplicationService) {
        this.ownerCardApplicationService = ownerCardApplicationService;
    }

    @PostMapping
    public ResponseEntity<OwnerCardResponse> createOwnerCard(@Valid @RequestBody CreateOwnerCardRequest request) {
        OwnerCard ownerCard = ownerCardApplicationService.createOwnerCard(request.toCommand());

        return ResponseEntity
                .created(URI.create("/api/owner-cards/" + ownerCard.getId()))
                .body(OwnerCardResponse.from(ownerCard));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerCardResponse> getOwnerCard(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(OwnerCardResponse.from(ownerCardApplicationService.getOwnerCard(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OwnerCardResponse> updateOwnerCard(
            @PathVariable @Positive Long id,
            @Valid @RequestBody UpdateOwnerCardRequest request
    ) {
        return ResponseEntity.ok(OwnerCardResponse.from(
                ownerCardApplicationService.updateOwnerCard(id, request.toCommand())
        ));
    }

    @PostMapping("/{id}/dogs")
    public ResponseEntity<OwnerCardResponse> addDog(
            @PathVariable @Positive Long id,
            @Valid @RequestBody AddDogRequest request
    ) {
        return ResponseEntity.ok(OwnerCardResponse.from(
                ownerCardApplicationService.addDog(id, new DogId(request.dogId()))
        ));
    }

    public record CreateOwnerCardRequest(
            @NotBlank @Size(max = 100) String firstName,
            @NotBlank @Size(max = 100) String lastName,
            @NotBlank @Size(max = 30) String phoneNumber,
            @NotBlank @Email @Size(max = 255) String email
    ) {

        CreateOwnerCardCommand toCommand() {
            return new CreateOwnerCardCommand(firstName, lastName, phoneNumber, email);
        }
    }

    public record UpdateOwnerCardRequest(
            @NotBlank @Size(max = 30) String phoneNumber,
            @NotBlank @Email @Size(max = 255) String email
    ) {

        UpdateOwnerCardCommand toCommand() {
            return new UpdateOwnerCardCommand(phoneNumber, email);
        }
    }

    public record AddDogRequest(@NotNull @Positive Long dogId) {
    }

    public record OwnerCardResponse(
            Long id,
            Long ownerId,
            String firstName,
            String lastName,
            String phoneNumber,
            String email,
            Set<Long> dogIds
    ) {

        static OwnerCardResponse from(OwnerCard ownerCard) {
            Owner owner = ownerCard.getOwner();
            return new OwnerCardResponse(
                    ownerCard.getId(),
                    owner.getId(),
                    owner.getFirstName(),
                    owner.getLastName(),
                    owner.getPhoneNumber(),
                    owner.getEmail(),
                    ownerCard.getDogIds().stream().map(DogId::id).collect(java.util.stream.Collectors.toSet())
            );
        }
    }
}

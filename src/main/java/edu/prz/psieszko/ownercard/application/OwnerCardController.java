package edu.prz.psieszko.ownercard.application;

import edu.prz.psieszko.ownercard.application.OwnerCardApplicationService.CreateOwnerCardCommand;
import edu.prz.psieszko.ownercard.domain.Owner;
import edu.prz.psieszko.ownercard.domain.OwnerCard;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public record CreateOwnerCardRequest(
            @NotBlank String firstName,
            @NotBlank String lastName,
            @NotBlank String phoneNumber,
            @NotBlank @Email String email
    ) {

        CreateOwnerCardCommand toCommand() {
            return new CreateOwnerCardCommand(firstName, lastName, phoneNumber, email);
        }
    }

    public record OwnerCardResponse(
            Long id,
            Long ownerId,
            String firstName,
            String lastName,
            String phoneNumber,
            String email
    ) {

        static OwnerCardResponse from(OwnerCard ownerCard) {
            Owner owner = ownerCard.getOwner();
            return new OwnerCardResponse(
                    ownerCard.getId(),
                    owner.getId(),
                    owner.getFirstName(),
                    owner.getLastName(),
                    owner.getPhoneNumber(),
                    owner.getEmail()
            );
        }
    }
}

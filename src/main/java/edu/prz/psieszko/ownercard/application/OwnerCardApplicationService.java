package edu.prz.psieszko.ownercard.application;

import edu.prz.psieszko.ownercard.domain.OwnerCard;
import edu.prz.psieszko.ownercard.domain.OwnerCardFactory;
import edu.prz.psieszko.ownercard.domain.OwnerCardRepository;
import edu.prz.psieszko.shared.identity.DogId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerCardApplicationService {

    private final OwnerCardRepository ownerCardRepository;
    private final OwnerCardFactory ownerCardFactory;

    @Transactional
    public OwnerCard createOwnerCard(CreateOwnerCardCommand command) {
        OwnerCard ownerCard = ownerCardFactory.create(new OwnerCardFactory.Input(
                command.firstName(),
                command.lastName(),
                command.phoneNumber(),
                command.email()
        ));

        return ownerCardRepository.save(ownerCard);
    }

    @Transactional(readOnly = true)
    public OwnerCard getOwnerCard(Long ownerCardId) {
        return ownerCardRepository.findById(ownerCardId)
                .orElseThrow(() -> new java.util.NoSuchElementException("Owner card does not exist"));
    }

    @Transactional
    public OwnerCard updateOwnerCard(Long ownerCardId, UpdateOwnerCardCommand command) {
        OwnerCard ownerCard = getOwnerCard(ownerCardId);
        ownerCard.updateOwnerContact(command.phoneNumber(), command.email());

        return ownerCardRepository.save(ownerCard);
    }

    @Transactional
    public OwnerCard addDog(Long ownerCardId, DogId dogId) {
        OwnerCard ownerCard = getOwnerCard(ownerCardId);
        ownerCard.addDog(dogId);

        return ownerCardRepository.save(ownerCard);
    }

    public record CreateOwnerCardCommand(
            String firstName,
            String lastName,
            String phoneNumber,
            String email
    ) {
    }

    public record UpdateOwnerCardCommand(
            String phoneNumber,
            String email
    ) {
    }
}

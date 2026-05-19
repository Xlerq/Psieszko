package edu.prz.psieszko.ownercard.application;

import edu.prz.psieszko.ownercard.domain.OwnerCard;
import edu.prz.psieszko.ownercard.domain.OwnerCardFactory;
import edu.prz.psieszko.ownercard.domain.OwnerCardRepository;
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
        OwnerCard ownerCard = ownerCardFactory.create(
                command.firstName(),
                command.lastName(),
                command.phoneNumber(),
                command.email()
        );

        return ownerCardRepository.save(ownerCard);
    }

    public record CreateOwnerCardCommand(
            String firstName,
            String lastName,
            String phoneNumber,
            String email
    ) {
    }
}

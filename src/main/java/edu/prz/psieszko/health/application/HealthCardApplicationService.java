package edu.prz.psieszko.health.application;

import edu.prz.psieszko.foundation.domain.NotExistsException;
import edu.prz.psieszko.health.domain.HealthCard;
import edu.prz.psieszko.health.domain.HealthCardFactory;
import edu.prz.psieszko.health.domain.HealthCardRepository;
import edu.prz.psieszko.health.domain.Medicine;
import edu.prz.psieszko.health.domain.Vaccination;
import edu.prz.psieszko.health.domain.VeterinaryVisit;
import edu.prz.psieszko.shared.identity.DogId;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HealthCardApplicationService {

    private final HealthCardFactory healthCardFactory;
    private final HealthCardRepository healthCardRepository;

    @Transactional
    public HealthCard createHealthCard(CreateHealthCardCommand command) {
        HealthCard healthCard = healthCardFactory.create(new HealthCardFactory.Input(command.dogId()));
        return healthCardRepository.save(healthCard);
    }

    @Transactional(readOnly = true)
    public HealthCard getHealthCard(Long id) {
        return healthCardRepository.findById(id)
                .orElseThrow(() -> NotExistsException.of("Health card does not exist"));
    }

    @Transactional(readOnly = true)
    public HealthCard getHealthCardByDogId(DogId dogId) {
        return healthCardRepository.findByDogId(dogId)
                .orElseThrow(() -> NotExistsException.of("Health card does not exist"));
    }

    @Transactional
    public HealthCard registerVeterinaryVisit(Long id, VeterinaryVisit veterinaryVisit) {
        HealthCard healthCard = getHealthCard(id);
        healthCard.registerVeterinaryVisit(veterinaryVisit);
        return healthCardRepository.save(healthCard);
    }

    @Transactional
    public HealthCard replaceVaccinations(Long id, Set<Vaccination> vaccinations) {
        HealthCard healthCard = getHealthCard(id);
        healthCard.replaceVaccinations(vaccinations);
        return healthCardRepository.save(healthCard);
    }

    @Transactional
    public HealthCard replaceMedicines(Long id, Set<Medicine> medicines) {
        HealthCard healthCard = getHealthCard(id);
        healthCard.replaceMedicines(medicines);
        return healthCardRepository.save(healthCard);
    }

    public record CreateHealthCardCommand(DogId dogId) {
    }
}

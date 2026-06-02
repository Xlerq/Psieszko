package edu.prz.psieszko.health.domain;

import edu.prz.psieszko.shared.identity.DogId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCardRepository extends JpaRepository<HealthCard, Long> {

    Optional<HealthCard> findByDogId(DogId dogId);
}

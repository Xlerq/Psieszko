package edu.prz.psieszko.health.domain;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import edu.prz.psieszko.shared.identity.DogId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Aggregate root for the health bounded context.
 *
 * Associations:
 * - Dog
 * - Veterinarian
 * - VeterinaryVisit
 * - Vaccination
 * - Medicine
 */
@Entity
@Table(name = "health_cards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthCard extends BaseEntity {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "dog_id", nullable = false))
    private DogId dogId;

    @ElementCollection
    @CollectionTable(name = "health_card_veterinary_visits", joinColumns = @JoinColumn(name = "health_card_id"))
    private Set<VeterinaryVisit> veterinaryVisits = new LinkedHashSet<>();

    @ElementCollection
    @CollectionTable(name = "health_card_vaccinations", joinColumns = @JoinColumn(name = "health_card_id"))
    private Set<Vaccination> vaccinations = new LinkedHashSet<>();

    @ElementCollection
    @CollectionTable(name = "health_card_medicines", joinColumns = @JoinColumn(name = "health_card_id"))
    private Set<Medicine> medicines = new LinkedHashSet<>();

    HealthCard(DogId dogId) {
        this.dogId = requireDogId(dogId);
    }

    public void registerVeterinaryVisit(VeterinaryVisit veterinaryVisit) {
        if (veterinaryVisit == null) {
            throw new IllegalArgumentException("Veterinary visit cannot be null");
        }
        veterinaryVisits.add(veterinaryVisit);
    }

    public void addVaccination(Vaccination vaccination) {
        if (vaccination == null) {
            throw new IllegalArgumentException("Vaccination cannot be null");
        }
        vaccinations.add(vaccination);
    }

    public void replaceVaccinations(Set<Vaccination> vaccinations) {
        this.vaccinations.clear();
        if (vaccinations == null) {
            return;
        }
        vaccinations.forEach(this::addVaccination);
    }

    public void addMedicine(Medicine medicine) {
        if (medicine == null) {
            throw new IllegalArgumentException("Medicine cannot be null");
        }
        medicines.add(medicine);
    }

    public void replaceMedicines(Set<Medicine> medicines) {
        this.medicines.clear();
        if (medicines == null) {
            return;
        }
        medicines.forEach(this::addMedicine);
    }

    public Set<VeterinaryVisit> getVeterinaryVisits() {
        return Collections.unmodifiableSet(veterinaryVisits);
    }

    public Set<Vaccination> getVaccinations() {
        return Collections.unmodifiableSet(vaccinations);
    }

    public Set<Medicine> getMedicines() {
        return Collections.unmodifiableSet(medicines);
    }

    private DogId requireDogId(DogId dogId) {
        if (dogId == null) {
            throw new IllegalArgumentException("Dog id cannot be null");
        }
        return dogId;
    }
}

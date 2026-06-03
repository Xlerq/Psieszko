package edu.prz.psieszko.ownercard.domain;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import edu.prz.psieszko.shared.identity.DogId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Aggregate root for the owner card bounded context.
 *
 * Associations:
 * - Owner
 * - Dog
 */
@Entity
@Table(name = "owner_cards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class OwnerCard extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ElementCollection
    @CollectionTable(name = "owner_card_dogs", joinColumns = @JoinColumn(name = "owner_card_id"))
    @AttributeOverride(name = "id", column = @Column(name = "dog_id", nullable = false))
    private Set<DogId> dogIds = new LinkedHashSet<>();

    OwnerCard(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null");
        }
        this.owner = owner;
    }

    public void addDog(DogId dogId) {
        if (dogId == null) {
            throw new IllegalArgumentException("Dog id cannot be null");
        }
        dogIds.add(dogId);
    }

    public void updateOwnerContact(String phoneNumber, String email) {
        owner.updateContact(phoneNumber, email);
    }

    public Set<DogId> getDogIds() {
        return Collections.unmodifiableSet(dogIds);
    }
}

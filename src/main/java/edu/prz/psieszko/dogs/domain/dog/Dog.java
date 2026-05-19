package edu.prz.psieszko.dogs.domain.dog;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import edu.prz.psieszko.shared.identity.OwnerCardId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Dog extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private Breed breed;

    @Enumerated(EnumType.STRING)
    private Diet diet;

    @Embedded
    private BehavioralProfile behavioralProfile;

    @Enumerated(EnumType.STRING)
    private AnimalTrait animalTrait;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "owner_card_id"))
    private OwnerCardId ownerCardId;
}

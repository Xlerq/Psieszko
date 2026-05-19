package edu.prz.psieszko.dogs.domain.dog;

import edu.prz.psieszko.shared.identity.OwnerCardId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
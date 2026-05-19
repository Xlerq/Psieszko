package edu.prz.psieszko.ownercard.domain;

import org.springframework.stereotype.Component;

/**
 * Factory responsible for creating OwnerCard aggregates.
 */
@Component
public class OwnerCardFactory {

    public OwnerCard create(String firstName, String lastName, String phoneNumber, String email) {
        return new OwnerCard(new Owner(firstName, lastName, phoneNumber, email));
    }
}

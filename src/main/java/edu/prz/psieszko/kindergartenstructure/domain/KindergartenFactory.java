package edu.prz.psieszko.kindergartenstructure.domain;

import edu.prz.psieszko.foundation.domain.StandardFactory;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class KindergartenFactory implements StandardFactory<String, Kindergarten> {

    @Override
    public Kindergarten create(String input) {

        val kindergarten = new Kindergarten();

        kindergarten.setName(input);

        return kindergarten;
    }
}
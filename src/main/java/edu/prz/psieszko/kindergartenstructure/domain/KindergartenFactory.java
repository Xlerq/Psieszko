package edu.prz.psieszko.kindergartenstructure.domain;

import edu.prz.psieszko.foundation.domain.StandardFactory;
import org.springframework.stereotype.Component;

@Component
public class KindergartenFactory implements StandardFactory<String, Kindergarten> {

    @Override
    public Kindergarten create(String input) {
        return new Kindergarten(input);
    }
}

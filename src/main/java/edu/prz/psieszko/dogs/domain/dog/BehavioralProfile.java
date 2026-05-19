package edu.prz.psieszko.dogs.domain.dog;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class BehavioralProfile {
    private boolean aggressive;
    private boolean sociable;
    private boolean trained;
}
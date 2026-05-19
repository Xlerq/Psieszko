package edu.prz.psieszko.dogs.domain.dog;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Dog extends BaseEntity {

  String name;
}

package edu.prz.psieszko.dogs.domain.dog;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Dog {

  @Id
  Long id;

  String name;
}

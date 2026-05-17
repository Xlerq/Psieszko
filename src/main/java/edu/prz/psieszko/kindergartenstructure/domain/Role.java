package edu.prz.psieszko.kindergartenstructure.domain;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    String name;
}
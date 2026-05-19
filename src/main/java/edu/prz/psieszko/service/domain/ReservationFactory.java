package edu.prz.psieszko.service.domain;

import edu.prz.psieszko.shared.identity.DogId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ReservationFactory {
    Reservation create(DogId dogId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal paymentAmount);
}
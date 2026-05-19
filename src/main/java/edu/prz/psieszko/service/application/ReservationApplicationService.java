package edu.prz.psieszko.service.application;

import edu.prz.psieszko.service.domain.Reservation;
import edu.prz.psieszko.service.domain.ReservationFactory;
import edu.prz.psieszko.service.domain.ReservationRepository;
import edu.prz.psieszko.shared.identity.DogId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationApplicationService {

    private final ReservationFactory reservationFactory;
    private final ReservationRepository reservationRepository;

    @Transactional
    public Reservation createReservation(DogId dogId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal paymentAmount) {
        Reservation reservation = reservationFactory.create(dogId, startDate, endDate, paymentAmount);
        return reservationRepository.save(reservation);
    }
}
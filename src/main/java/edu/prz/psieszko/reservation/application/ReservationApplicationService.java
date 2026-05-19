package edu.prz.psieszko.reservation.application;

import edu.prz.psieszko.reservation.domain.reservation.Reservation;
import edu.prz.psieszko.reservation.domain.reservation.ReservationFactory;
import edu.prz.psieszko.reservation.domain.reservation.ReservationRepository;
import edu.prz.psieszko.shared.identity.DogId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationApplicationService {

    private final ReservationFactory reservationFactory;
    private final ReservationRepository reservationRepository;

    public Reservation createReservation(DogId dogId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal paymentAmount) {
        Reservation reservation = reservationFactory.create(dogId, startDate, endDate, paymentAmount);
        return reservationRepository.save(reservation);
    }
}
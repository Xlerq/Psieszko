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

    @Transactional(readOnly = true)
    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new java.util.NoSuchElementException("Reservation does not exist"));
    }

    @Transactional
    public Reservation changeReservationDate(Long reservationId, LocalDateTime startDate, LocalDateTime endDate) {
        Reservation reservation = getReservation(reservationId);
        reservation.changeDate(startDate, endDate);

        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation cancelReservation(Long reservationId) {
        Reservation reservation = getReservation(reservationId);
        reservation.cancel();

        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation markPaymentAsPaid(Long reservationId) {
        Reservation reservation = getReservation(reservationId);
        reservation.markPaymentAsPaid();

        return reservationRepository.save(reservation);
    }
}

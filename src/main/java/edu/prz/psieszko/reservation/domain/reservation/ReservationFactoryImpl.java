package edu.prz.psieszko.reservation.domain.reservation;

import edu.prz.psieszko.shared.identity.DogId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class ReservationFactoryImpl implements ReservationFactory {

    @Override
    public Reservation create(DogId dogId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal paymentAmount) {
        Reservation reservation = new Reservation();
        reservation.setDogId(dogId);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setStatus(ReservationStatus.CREATED);

        Payment payment = new Payment();
        payment.setAmount(paymentAmount);
        payment.setStatus(PaymentStatus.PENDING);
        reservation.setPayment(payment);

        return reservation;
    }
}
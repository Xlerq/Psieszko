package edu.prz.psieszko.service.domain;

import edu.prz.psieszko.shared.identity.DogId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class ReservationFactoryImpl implements ReservationFactory {

    @Override
    public Reservation create(DogId dogId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal paymentAmount) {
        if (dogId == null) {
            throw new IllegalArgumentException("Dog id cannot be null");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (!startDate.isBefore(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        if (paymentAmount == null || paymentAmount.signum() < 0) {
            throw new IllegalArgumentException("Payment amount must be non-negative");
        }

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
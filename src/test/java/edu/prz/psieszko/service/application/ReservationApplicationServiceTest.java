package edu.prz.psieszko.service.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.prz.psieszko.service.domain.PaymentStatus;
import edu.prz.psieszko.service.domain.Reservation;
import edu.prz.psieszko.service.domain.ReservationRepository;
import edu.prz.psieszko.service.domain.ReservationStatus;
import edu.prz.psieszko.shared.identity.DogId;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:psieszko-reservation-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class ReservationApplicationServiceTest {

    @Autowired
    private ReservationApplicationService reservationApplicationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
    }

    @Test
    void createsReservationThroughApplicationService() {
        LocalDateTime start = LocalDateTime.of(2026, 6, 1, 8, 0);
        LocalDateTime end = LocalDateTime.of(2026, 6, 5, 16, 0);

        Reservation reservation = reservationApplicationService.createReservation(
                new DogId(1L),
                start,
                end,
                new BigDecimal("250.00")
        );

        assertNotNull(reservation.getId());
        assertEquals(new DogId(1L), reservation.getDogId());
        assertEquals(ReservationStatus.CREATED, reservation.getStatus());
        assertEquals(PaymentStatus.PENDING, reservation.getPayment().getStatus());
        assertEquals(new BigDecimal("250.00"), reservation.getPayment().getAmount());
    }

    @Test
    void changesReservationDate() {
        Reservation reservation = reservationApplicationService.createReservation(
                new DogId(1L),
                LocalDateTime.of(2026, 6, 1, 8, 0),
                LocalDateTime.of(2026, 6, 5, 16, 0),
                new BigDecimal("250.00")
        );

        LocalDateTime newStart = LocalDateTime.of(2026, 7, 1, 8, 0);
        LocalDateTime newEnd = LocalDateTime.of(2026, 7, 5, 16, 0);
        reservation.changeDate(newStart, newEnd);

        assertEquals(newStart, reservation.getStartDate());
        assertEquals(newEnd, reservation.getEndDate());
    }

    @Test
    void marksPaymentAsPaid() {
        Reservation reservation = reservationApplicationService.createReservation(
                new DogId(1L),
                LocalDateTime.of(2026, 6, 1, 8, 0),
                LocalDateTime.of(2026, 6, 5, 16, 0),
                new BigDecimal("250.00")
        );

        reservation.markPaymentAsPaid();

        assertEquals(PaymentStatus.PAID, reservation.getPayment().getStatus());
    }

    @Test
    void cancelsReservation() {
        Reservation reservation = reservationApplicationService.createReservation(
                new DogId(1L),
                LocalDateTime.of(2026, 6, 1, 8, 0),
                LocalDateTime.of(2026, 6, 5, 16, 0),
                new BigDecimal("250.00")
        );

        reservation.cancel();

        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    }

    @Test
    void rejectsInvalidDateOrder() {
        assertThrows(IllegalArgumentException.class, () ->
                reservationApplicationService.createReservation(
                        new DogId(1L),
                        LocalDateTime.of(2026, 6, 5, 16, 0),
                        LocalDateTime.of(2026, 6, 1, 8, 0),
                        new BigDecimal("250.00")
                )
        );
    }
}
package edu.prz.psieszko.service.application;

import edu.prz.psieszko.service.domain.PaymentStatus;
import edu.prz.psieszko.service.domain.Reservation;
import edu.prz.psieszko.service.domain.ReservationStatus;
import edu.prz.psieszko.shared.identity.DogId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservations")
public class ReservationController {

    private final ReservationApplicationService reservationApplicationService;

    public ReservationController(ReservationApplicationService reservationApplicationService) {
        this.reservationApplicationService = reservationApplicationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody CreateReservationRequest request
    ) {
        Reservation reservation = reservationApplicationService.createReservation(
                new DogId(request.dogId()),
                request.startDate(),
                request.endDate(),
                request.paymentAmount()
        );

        return ResponseEntity
                .created(URI.create("/api/reservations/" + reservation.getId()))
                .body(ReservationResponse.from(reservation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(ReservationResponse.from(reservationApplicationService.getReservation(id)));
    }

    @PatchMapping("/{id}/date")
    public ResponseEntity<ReservationResponse> changeReservationDate(
            @PathVariable @Positive Long id,
            @Valid @RequestBody ChangeReservationDateRequest request
    ) {
        return ResponseEntity.ok(ReservationResponse.from(
                reservationApplicationService.changeReservationDate(id, request.startDate(), request.endDate())
        ));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(ReservationResponse.from(reservationApplicationService.cancelReservation(id)));
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<ReservationResponse> markPaymentAsPaid(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(ReservationResponse.from(reservationApplicationService.markPaymentAsPaid(id)));
    }

    public record CreateReservationRequest(
            @NotNull @Positive Long dogId,
            @NotNull LocalDateTime startDate,
            @NotNull LocalDateTime endDate,
            @NotNull @DecimalMin(value = "0.00") BigDecimal paymentAmount
    ) {
    }

    public record ChangeReservationDateRequest(
            @NotNull LocalDateTime startDate,
            @NotNull LocalDateTime endDate
    ) {
    }

    public record ReservationResponse(
            Long id,
            Long dogId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            ReservationStatus status,
            PaymentResponse payment
    ) {

        static ReservationResponse from(Reservation reservation) {
            return new ReservationResponse(
                    reservation.getId(),
                    reservation.getDogId().id(),
                    reservation.getStartDate(),
                    reservation.getEndDate(),
                    reservation.getStatus(),
                    PaymentResponse.from(reservation)
            );
        }
    }

    public record PaymentResponse(
            BigDecimal amount,
            PaymentStatus status
    ) {

        static PaymentResponse from(Reservation reservation) {
            return new PaymentResponse(
                    reservation.getPayment().getAmount(),
                    reservation.getPayment().getStatus()
            );
        }
    }
}

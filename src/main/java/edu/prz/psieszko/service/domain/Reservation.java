package edu.prz.psieszko.service.domain;

import edu.prz.psieszko.foundation.domain.BaseEntity;
import edu.prz.psieszko.shared.identity.DogId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Reservation extends BaseEntity {

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "dog_id"))
    private DogId dogId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Embedded
    @AttributeOverride(name = "status", column = @Column(name = "payment_status"))
    @AttributeOverride(name = "amount", column = @Column(name = "payment_amount"))
    private Payment payment;

    public void changeDate(LocalDateTime newStartDate, LocalDateTime newEndDate) {
        if (newStartDate == null || newEndDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (!newStartDate.isBefore(newEndDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        if (this.status == ReservationStatus.CANCELLED || this.status == ReservationStatus.COMPLETED) {
            throw new IllegalStateException("Cannot change date of cancelled or completed reservation");
        }
        this.startDate = newStartDate;
        this.endDate = newEndDate;
    }

    public void markPaymentAsPaid() {
        if (this.payment == null) {
            throw new IllegalStateException("Reservation has no payment");
        }
        this.payment.markAsPaid();
    }

    public void cancel() {
        if (this.status == ReservationStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel completed reservation");
        }
        this.status = ReservationStatus.CANCELLED;
    }
}
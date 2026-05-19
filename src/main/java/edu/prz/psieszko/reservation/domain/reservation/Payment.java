package edu.prz.psieszko.reservation.domain.reservation;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;

@Embeddable
@Data
public class Payment {

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public void markAsPaid() {
        this.status = PaymentStatus.PAID;
    }
}
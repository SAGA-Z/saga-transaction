package org.sagaz.payment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.sagaz.payment.infrastructure.ErrorMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payment extends AbstractAggregateRoot<Payment> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "uuid-char")
    @Column(nullable = false, unique = true)
    private final UUID uuid = UUID.randomUUID();

    @Type(type = "uuid-char")
    private UUID purchaseUuid;
    private Long paymentPrice;

    public Payment(UUID purchaseUuid, Long paymentPrice) {
        this.purchaseUuid = purchaseUuid;
        this.paymentPrice = paymentPrice;
    }

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public void publishPaymentCreatedEvent(ErrorMode errorMode) {
        registerEvent(
                new PaymentCreatedEvent(this.uuid, this.purchaseUuid, errorMode)
        );
    }
}

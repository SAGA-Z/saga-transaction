package org.sagaz.delivery.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
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
public class Delivery extends AbstractAggregateRoot<Delivery> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "uuid-char")
    @Column(nullable = false, unique = true)
    private final UUID uuid = UUID.randomUUID();
    //@Type(type = "uuid-char")
    //private UUID paymentUuid;
    @Type(type = "uuid-char")
    private UUID purchaseUuid;

    private String receiverMessengerId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus = DeliveryStatus.PREPARING;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Delivery(
            UUID purchaseUuid,
            String receiverMessengerId
    ) {
        this.purchaseUuid = purchaseUuid;
        this.receiverMessengerId = receiverMessengerId;
    }

    public void deliver() {
        if (deliveryStatus != DeliveryStatus.PREPARING)
            throw new NotDeliverableException();
        this.deliveryStatus = DeliveryStatus.DELIVERING;
//        this.publishDeliveringEvent();
    }

    public void prepare() {
        this.deliveryStatus = DeliveryStatus.PREPARING;
    }

    public void publishDeliveringEvent() {
        registerEvent(
                new DeliveringEvent(
                        this.uuid,
                        this.receiverMessengerId
                )
        );
    }

    public void delivered() {
        this.deliveryStatus = DeliveryStatus.DELIVERED;
    }
}

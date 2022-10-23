package org.sagaz.purchase.domain;

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
public class Purchase extends AbstractAggregateRoot<Purchase> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "uuid-char")
    @Column(nullable = false, unique = true)
    private final UUID uuid = UUID.randomUUID();

    private String name;

    private Boolean confirmed = false;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Purchase(String name) {
        this.name = name;
    }

    public void confirm(ErrorMode errorMode) {
        if (!this.confirmed) {
            this.publishPurchaseConfirmedEvent(errorMode);
        }
        this.confirmed = true;
    }

    public void unConfirm() {
        this.confirmed = false;
    }

    public void publishPurchaseConfirmedEvent(ErrorMode errorMode) {
        registerEvent(
                new PurchaseConfirmedEvent(this.uuid, errorMode)
        );
    }
}

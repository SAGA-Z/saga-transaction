package org.sagaz.delivery.presentation;

import lombok.RequiredArgsConstructor;
import org.sagaz.delivery.application.DeliveryCreationService;
import org.sagaz.delivery.application.DeliveryDeletionService;
import org.sagaz.delivery.domain.Delivery;
import org.sagaz.delivery.infrastructure.DeliveryCreatedEvent;
import org.sagaz.delivery.infrastructure.DeliveryRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryCreationService deliveryCreationService;
    private final DeliveryDeletionService deliveryDeletionService;


    @PostMapping("/create")
    public DeliveryCreatedEvent create(@RequestBody DeliveryRequestDto requestDTO) {
        System.out.println("DeliveryController.create");
        Delivery delivery = deliveryCreationService.create(requestDTO.purchaseUuid);
        return new DeliveryCreatedEvent(delivery.getUuid(), requestDTO.purchaseUuid, requestDTO.errorMode);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody DeliveryRequestDto requestDTO) {
        System.out.println("DeliveryController.delete");
        deliveryDeletionService.deleteByPurchaseUuid(requestDTO.purchaseUuid);
    }
}
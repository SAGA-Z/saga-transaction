package org.sagaz.purchase.presentation;

import lombok.RequiredArgsConstructor;
import org.sagaz.purchase.application.*;
import org.sagaz.purchase.common.CommonResponse;
import org.sagaz.purchase.common.ResponseService;
import org.sagaz.purchase.domain.ErrorMode;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class PurchaseConfirmationController {
    private final PurchaseConfirmationService purchaseConfirmationService;
    private final PurchaseCreationService purchaseCreationService;
    private final ResponseService responseService;

    @PatchMapping("{uuid}/confirm")
    public CommonResponse<Void> confirm(
            @PathVariable UUID uuid,
            @RequestParam ErrorMode errorMode
    ) throws PurchaseNotFoundException {
        purchaseConfirmationService.confirm(uuid, errorMode);

        return responseService.getSuccessResponse();
    }

    @PatchMapping("test/confirm")
    public CommonResponse<Void> confirm() throws PurchaseNotFoundException {
        PurchaseCreationResponse hi = purchaseCreationService.create(new PurchaseCreationRequest("hi"));
        purchaseConfirmationService.confirm(hi.uuid, ErrorMode.NO_ERROR);
        return responseService.getSuccessResponse();
    }

    @PatchMapping("test/confirm-payment-error")
    public CommonResponse<Void> confirmPaymentError() throws PurchaseNotFoundException {
        PurchaseCreationResponse hi = purchaseCreationService.create(new PurchaseCreationRequest("hi"));
        purchaseConfirmationService.confirm(hi.uuid, ErrorMode.PAYMENT_ERROR);
        return responseService.getSuccessResponse();
    }

    @PatchMapping("test/confirm-delivery-error")
    public CommonResponse<Void> confirmDeliveryError() throws PurchaseNotFoundException {
        PurchaseCreationResponse hi = purchaseCreationService.create(new PurchaseCreationRequest("hi"));
        purchaseConfirmationService.confirm(hi.uuid, ErrorMode.DELIVERY_ERROR);
        return responseService.getSuccessResponse();
    }
}

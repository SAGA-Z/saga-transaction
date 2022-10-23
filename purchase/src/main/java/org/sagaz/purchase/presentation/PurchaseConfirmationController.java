package org.sagaz.purchase.presentation;

import lombok.RequiredArgsConstructor;
import org.sagaz.purchase.application.PurchaseConfirmationService;
import org.sagaz.purchase.application.PurchaseNotFoundException;
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
    private final ResponseService responseService;

    @PatchMapping("{uuid}/confirm")
    public CommonResponse<Void> confirm(
            @PathVariable UUID uuid,
            @RequestParam ErrorMode errorMode
    ) throws PurchaseNotFoundException {
        purchaseConfirmationService.confirm(uuid, errorMode);

        return responseService.getSuccessResponse();
    }
}

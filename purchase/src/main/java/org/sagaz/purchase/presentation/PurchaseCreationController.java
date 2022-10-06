package org.sagaz.purchase.presentation;

import lombok.RequiredArgsConstructor;
import org.sagaz.purchase.application.PurchaseCreationRequest;
import org.sagaz.purchase.application.PurchaseCreationResponse;
import org.sagaz.purchase.application.PurchaseCreationService;
import org.sagaz.purchase.common.CommonResponse;
import org.sagaz.purchase.common.ResponseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PurchaseCreationController {
    private final PurchaseCreationService creationService;
    private final ResponseService responseService;

    @PostMapping
    public CommonResponse<PurchaseCreationResponse> create(@RequestBody PurchaseCreationRequest request) {
        return responseService.toSuccessResponse(creationService.create(request));
    }
}

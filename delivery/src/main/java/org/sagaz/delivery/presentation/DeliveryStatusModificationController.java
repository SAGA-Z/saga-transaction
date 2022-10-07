package org.sagaz.delivery.presentation;

import lombok.RequiredArgsConstructor;
import org.sagaz.delivery.application.DeliveryNotFoundException;
import org.sagaz.delivery.application.DeliveryStatusModificationService;
import org.sagaz.delivery.common.CommonResponse;
import org.sagaz.delivery.common.ResponseService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class DeliveryStatusModificationController {
    private final DeliveryStatusModificationService modificationService;
    private final ResponseService responseService;

    @PatchMapping("{uuid}/deliver")
    public CommonResponse<Void> delivery(@PathVariable UUID uuid) throws DeliveryNotFoundException {
        modificationService.deliver(uuid);

        return responseService.getSuccessResponse();
    }

    @PatchMapping("{uuid}/delivered")
    public CommonResponse<Void> delivered(@PathVariable UUID uuid) throws DeliveryNotFoundException {
        modificationService.delivered(uuid);

        return responseService.getSuccessResponse();
    }
}

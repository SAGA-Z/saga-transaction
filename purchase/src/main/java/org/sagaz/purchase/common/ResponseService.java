package org.sagaz.purchase.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseService {
    public CommonResponse<Void> getSuccessResponse() {
        return new CommonResponse<Void>(true, null);
    }

    public <T> CommonResponse<T> toSuccessResponse(T message) {
        return new CommonResponse<T>(true, message);
    }
}

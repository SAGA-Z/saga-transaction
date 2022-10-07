package org.sagaz.delivery.application;

import java.util.function.Supplier;

public class DeliveryNotFoundException extends Exception implements Supplier<DeliveryNotFoundException> {
    public DeliveryNotFoundException() {
        super("배송정보가 존재하지 않습니다.");
    }

    @Override
    public DeliveryNotFoundException get() {
        return null;
    }
}

package org.sagaz.purchase.application;

import java.util.function.Supplier;

public class PurchaseNotFoundException extends Exception implements Supplier<PurchaseNotFoundException> {
    public PurchaseNotFoundException() {
        super("구매정보가 존재하지 않습니다.");
    }

    @Override
    public PurchaseNotFoundException get() {
        return null;
    }
}

package com.alibou.security.store;

public enum StoreType {
    STORE_NONE(0), STORE_BRAND(1), STORE_SOHO(2), STORE_PERSONAL(3);

    private final int value;

    StoreType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

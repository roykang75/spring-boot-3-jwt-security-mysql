package com.alibou.security.common.enums;

public enum DeleteType {
    DELETED_NO(0), DELETED_YES(1);

    private final int value;

    DeleteType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

package com.alibou.security.store;

public class Brand implements CustomerType {

    @Override
    public boolean changeAddress() {
        return false;
    }

    @Override
    public boolean changeTelephone() {
        return false;
    }

    @Override
    public String getIdentificationCode() {
        return null;
    }
}

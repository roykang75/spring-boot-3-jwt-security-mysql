package com.alibou.security.advice.exception;

public class CDuplicatePolicyExistException extends RuntimeException {
    public CDuplicatePolicyExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public CDuplicatePolicyExistException(String msg) {
        super(msg);
    }

    public CDuplicatePolicyExistException() {
        super();
    }
}
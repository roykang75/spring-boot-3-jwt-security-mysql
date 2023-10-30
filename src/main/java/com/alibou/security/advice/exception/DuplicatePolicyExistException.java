package com.alibou.security.advice.exception;

public class DuplicatePolicyExistException extends RuntimeException {
    public DuplicatePolicyExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public DuplicatePolicyExistException(String msg) {
        super(msg);
    }

    public DuplicatePolicyExistException() {
        super();
    }
}
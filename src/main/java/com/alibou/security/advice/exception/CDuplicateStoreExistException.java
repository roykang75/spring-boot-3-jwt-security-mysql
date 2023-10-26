package com.alibou.security.advice.exception;

public class CDuplicateStoreExistException extends RuntimeException {
    public CDuplicateStoreExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public CDuplicateStoreExistException(String msg) {
        super(msg);
    }

    public CDuplicateStoreExistException() {
        super();
    }
}
package com.alibou.security.advice.exception;

public class DuplicateStoreExistException extends RuntimeException {
    public DuplicateStoreExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public DuplicateStoreExistException(String msg) {
        super(msg);
    }

    public DuplicateStoreExistException() {
        super();
    }
}
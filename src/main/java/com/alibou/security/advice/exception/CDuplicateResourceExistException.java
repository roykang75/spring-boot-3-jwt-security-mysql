package com.alibou.security.advice.exception;

public class CDuplicateResourceExistException extends RuntimeException {
    public CDuplicateResourceExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public CDuplicateResourceExistException(String msg) {
        super(msg);
    }

    public CDuplicateResourceExistException() {
        super();
    }
}
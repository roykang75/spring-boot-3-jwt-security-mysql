package com.alibou.security.advice.exception;

public class DuplicateResourceExistException extends RuntimeException {
    public DuplicateResourceExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public DuplicateResourceExistException(String msg) {
        super(msg);
    }

    public DuplicateResourceExistException() {
        super();
    }
}
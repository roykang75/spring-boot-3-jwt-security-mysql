package com.alibou.security.advice.exception;

public class ResourceNotExistException extends RuntimeException {
    public ResourceNotExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public ResourceNotExistException(String msg) {
        super(msg);
    }

    public ResourceNotExistException() {
        super();
    }
}
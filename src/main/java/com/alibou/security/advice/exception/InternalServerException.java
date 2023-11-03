package com.alibou.security.advice.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String msg, Throwable t) {
        super(msg, t);
    }

    public InternalServerException(String msg) {
        super(msg);
    }

    public InternalServerException() {
        super();
    }
}
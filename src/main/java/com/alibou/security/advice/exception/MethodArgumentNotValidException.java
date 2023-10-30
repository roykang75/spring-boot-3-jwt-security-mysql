package com.alibou.security.advice.exception;

public class MethodArgumentNotValidException extends RuntimeException {
    public MethodArgumentNotValidException(String msg, Throwable t) {
        super(msg, t);
    }

    public MethodArgumentNotValidException(String msg) {
        super(msg);
    }

    public MethodArgumentNotValidException() {
        super();
    }
}
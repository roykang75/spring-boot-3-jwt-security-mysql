package com.alibou.security.advice.exception;

public class CMethodArgumentNotValidException extends RuntimeException {
    public CMethodArgumentNotValidException(String msg, Throwable t) {
        super(msg, t);
    }

    public CMethodArgumentNotValidException(String msg) {
        super(msg);
    }

    public CMethodArgumentNotValidException() {
        super();
    }
}
package com.alibou.security.advice.exception;

public class JwtNotValidException extends RuntimeException {
    public JwtNotValidException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtNotValidException(String msg) {
        super(msg);
    }

    public JwtNotValidException() {
        super();
    }
}
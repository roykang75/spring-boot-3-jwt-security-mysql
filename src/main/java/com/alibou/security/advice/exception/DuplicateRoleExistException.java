package com.alibou.security.advice.exception;

public class DuplicateRoleExistException extends RuntimeException {
    public DuplicateRoleExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public DuplicateRoleExistException(String msg) {
        super(msg);
    }

    public DuplicateRoleExistException() {
        super();
    }
}
package com.alibou.security.advice.exception;

public class CDuplicateRoleExistException extends RuntimeException {
    public CDuplicateRoleExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public CDuplicateRoleExistException(String msg) {
        super(msg);
    }

    public CDuplicateRoleExistException() {
        super();
    }
}
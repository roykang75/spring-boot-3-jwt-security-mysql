package com.alibou.security.advice.exception;

public class CResourceSaveFailException extends RuntimeException {
    public CResourceSaveFailException(String msg, Throwable t) {
        super(msg, t);
    }

    public CResourceSaveFailException(String msg) {
        super(msg);
    }

    public CResourceSaveFailException() {
        super();
    }
}
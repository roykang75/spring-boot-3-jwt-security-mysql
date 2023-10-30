package com.alibou.security.advice.exception;

public class ResourceSaveFailException extends RuntimeException {
    public ResourceSaveFailException(String msg, Throwable t) {
        super(msg, t);
    }

    public ResourceSaveFailException(String msg) {
        super(msg);
    }

    public ResourceSaveFailException() {
        super();
    }
}
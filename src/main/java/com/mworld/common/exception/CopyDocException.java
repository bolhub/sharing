package com.mworld.common.exception;

public class CopyDocException extends Exception {
    public CopyDocException() {
        super();
    }

    public CopyDocException(String message) {
        super(message);
    }

    public CopyDocException(String message, Throwable cause) {
        super(message, cause);
    }

    public CopyDocException(Throwable cause) {
        super(cause);
    }

    protected CopyDocException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

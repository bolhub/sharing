package com.mworld.common.exception;

public class OutOfCapacityException extends Exception {
    public OutOfCapacityException() {
        super();
    }

    public OutOfCapacityException(String message) {
        super(message);
    }

    public OutOfCapacityException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfCapacityException(Throwable cause) {
        super(cause);
    }

    protected OutOfCapacityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

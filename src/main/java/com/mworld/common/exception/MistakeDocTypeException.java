package com.mworld.common.exception;

public class MistakeDocTypeException extends Exception {
    public MistakeDocTypeException() {
        super();
    }

    public MistakeDocTypeException(String message) {
        super(message);
    }

    public MistakeDocTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MistakeDocTypeException(Throwable cause) {
        super(cause);
    }

    protected MistakeDocTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

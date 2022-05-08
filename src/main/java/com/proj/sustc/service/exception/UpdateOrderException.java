package com.proj.sustc.service.exception;

public class UpdateOrderException extends ServiceException{
    public UpdateOrderException() {
        super();
    }

    public UpdateOrderException(String message) {
        super(message);
    }

    public UpdateOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateOrderException(Throwable cause) {
        super(cause);
    }

    protected UpdateOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

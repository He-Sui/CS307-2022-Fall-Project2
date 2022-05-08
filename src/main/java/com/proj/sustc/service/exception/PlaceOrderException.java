package com.proj.sustc.service.exception;

public class PlaceOrderException extends ServiceException{
    public PlaceOrderException() {
        super();
    }

    public PlaceOrderException(String message) {
        super(message);
    }

    public PlaceOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlaceOrderException(Throwable cause) {
        super(cause);
    }

    protected PlaceOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

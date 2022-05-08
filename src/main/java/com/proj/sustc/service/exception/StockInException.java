package com.proj.sustc.service.exception;

public class StockInException extends ServiceException {
    public StockInException() {
        super();
    }

    public StockInException(String message) {
        super(message);
    }

    public StockInException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockInException(Throwable cause) {
        super(cause);
    }

    protected StockInException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

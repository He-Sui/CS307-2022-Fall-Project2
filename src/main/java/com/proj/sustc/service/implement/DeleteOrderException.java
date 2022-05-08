package com.proj.sustc.service.implement;

import com.proj.sustc.service.exception.ServiceException;

public class DeleteOrderException extends ServiceException {
    public DeleteOrderException() {
        super();
    }

    public DeleteOrderException(String message) {
        super(message);
    }

    public DeleteOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteOrderException(Throwable cause) {
        super(cause);
    }

    protected DeleteOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

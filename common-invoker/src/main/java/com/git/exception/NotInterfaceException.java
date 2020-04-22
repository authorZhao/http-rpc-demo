package com.git.exception;

/**
 * @author authorZhao不是接口异常
 */
public class NotInterfaceException extends RuntimeException {
    public NotInterfaceException() {
        super();
    }

    public NotInterfaceException(String message) {
        super(message);
    }

    public NotInterfaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotInterfaceException(Throwable cause) {
        super(cause);
    }

    protected NotInterfaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

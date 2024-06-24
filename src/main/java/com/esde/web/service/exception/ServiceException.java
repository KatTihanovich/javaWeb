package com.esde.web.service.exception;

public class ServiceException extends Exception {

    public ServiceException() {

    }

    public ServiceException(String message) {
        super("Service have encountered an error: " + message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
package com.esde.web.dao.exception;

public class DaoException extends Exception {

    public DaoException() {

    }

    public DaoException(String message) {
        super("Dao have encountered an error: " + message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
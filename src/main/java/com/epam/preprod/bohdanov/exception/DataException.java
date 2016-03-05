package com.epam.preprod.bohdanov.exception;

public class DataException extends IllegalArgumentException {
    private static final long serialVersionUID = -307318559665264161L;

    public DataException(Throwable e) {
        throw new IllegalArgumentException(e);
    }
}

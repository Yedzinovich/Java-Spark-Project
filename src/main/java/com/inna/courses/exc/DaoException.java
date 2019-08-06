package com.inna.courses.exc;

public class DaoException extends Exception {
    private final Exception original;

    public DaoException(Exception original, String msg){
        super(msg);
        this.original = original;
    }
}

package com.CRUDApplication.exception.user;

public class AlreadyDisabledException extends RuntimeException{
    public AlreadyDisabledException(String message) { super(message); }
}

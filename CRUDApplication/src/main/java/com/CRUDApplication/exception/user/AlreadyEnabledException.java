package com.CRUDApplication.exception.user;

public class AlreadyEnabledException extends RuntimeException{
    public AlreadyEnabledException(String message){ super(message); }
}

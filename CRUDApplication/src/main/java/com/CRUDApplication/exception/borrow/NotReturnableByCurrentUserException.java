package com.CRUDApplication.exception.borrow;

public class NotReturnableByCurrentUserException extends RuntimeException {
    public NotReturnableByCurrentUserException(String message) {super(message);}
}

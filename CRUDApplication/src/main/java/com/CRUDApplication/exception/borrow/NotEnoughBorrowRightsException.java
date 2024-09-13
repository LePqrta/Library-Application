package com.CRUDApplication.exception.borrow;

public class NotEnoughBorrowRightsException extends RuntimeException{
    public NotEnoughBorrowRightsException(String message) { super(message); }
}

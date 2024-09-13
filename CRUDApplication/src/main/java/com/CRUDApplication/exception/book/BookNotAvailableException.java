package com.CRUDApplication.exception.book;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String message) {
        super(message);
    }
}
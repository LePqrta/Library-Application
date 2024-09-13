package com.CRUDApplication.exception.book;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException() {
        super("Author not found");
    }
}

package com.CRUDApplication.exception.user;

public class UsernameIsBeingUsedException extends RuntimeException {
    public UsernameIsBeingUsedException(String message) {super(message);}
}

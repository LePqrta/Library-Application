package com.CRUDApplication.exception.user;

public class SuperAdminNotFoundException extends RuntimeException {
    public SuperAdminNotFoundException(String message) {
        super(message);
    }
}

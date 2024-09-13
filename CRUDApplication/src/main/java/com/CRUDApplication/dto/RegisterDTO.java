package com.CRUDApplication.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterDTO implements Serializable {
    private String username;
    private String password;
    private String confirmPassword;
}

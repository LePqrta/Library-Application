package com.CRUDApplication.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDTO implements Serializable {
    private String username;
    private String password;
}

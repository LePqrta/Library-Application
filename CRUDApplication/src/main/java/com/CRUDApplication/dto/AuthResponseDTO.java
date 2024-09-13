package com.CRUDApplication.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthResponseDTO implements Serializable
{
    private String accessToken;
    private String tokenType = "Bearer";
    public AuthResponseDTO(String accessToken){
        this.accessToken = accessToken;
    }
}

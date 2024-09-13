package com.CRUDApplication.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class LoginResponse {
    @Getter
    private String token;

    private long expiresIn;

    private String isNotEnabled;
}
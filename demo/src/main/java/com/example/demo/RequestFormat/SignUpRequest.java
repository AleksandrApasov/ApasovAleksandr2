package com.example.demo.RequestFormat;

import lombok.Data;

import java.util.Date;

@Data
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String fio;
    private int balance;
    private Date birthDate;
}

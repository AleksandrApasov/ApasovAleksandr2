package com.example.demo.Services;

import org.springframework.security.core.Authentication;

public interface UserService {
    boolean changePhoneAndEmail(Authentication authentication,String phone, String email);
}

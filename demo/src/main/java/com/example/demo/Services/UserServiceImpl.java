package com.example.demo.Services;

import com.example.demo.Models.User;
import com.example.demo.SecurityServices.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserServiceImpl implements UserService {
    @Override
    public boolean changePhoneAndEmail(Authentication authentication,String phone, String email) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return false;
    }


}

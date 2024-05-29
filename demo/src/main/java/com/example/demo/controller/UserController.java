package com.example.demo.controller;

import com.example.demo.ResponseFormat.MessageResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/all")
    public MessageResponse allAccess() {
        return new MessageResponse("Server is up.....");
    }

    @GetMapping("/changePhoneAndEmail")
    @PreAuthorize("isAuthenticated()")
    public MessageResponse userAccess() {


        return new MessageResponse
                ("Congratulations! You are an authenticated user.");
    }
}

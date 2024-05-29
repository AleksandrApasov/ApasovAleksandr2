package com.example.demo.controller;

import com.example.demo.Models.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.RequestFormat.LoginRequest;
import com.example.demo.RequestFormat.SignUpRequest;
import com.example.demo.ResponseFormat.JwtResponse;
import com.example.demo.ResponseFormat.MessageResponse;
import com.example.demo.SecurityServices.UserDetailsImpl;
import com.example.demo.SecurityServices.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateuser
            (@RequestBody LoginRequest loginRequest) {

        org.springframework.security.core.Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken
                        (loginRequest.getUsername(),
                                loginRequest.getPassword()));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl)
                authentication.getPrincipal();

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser
            (@RequestBody SignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest
                .getUsername())) {

            return ResponseEntity.badRequest()
                    .body(new MessageResponse
                            ("Error: username is already taken!"));
        }

        if (userRepository.existsByEmail
                (signUpRequest.getEmail())) {

            return ResponseEntity.badRequest()
                    .body(new MessageResponse
                            ("Error: Email is already in use!"));
        }

        // Create new user account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getBalance(),signUpRequest.getBirthDate(),
                signUpRequest.getFio());

        userRepository.save(user);

        return ResponseEntity
                .ok(new MessageResponse("user registered successfully!"));
    }
}

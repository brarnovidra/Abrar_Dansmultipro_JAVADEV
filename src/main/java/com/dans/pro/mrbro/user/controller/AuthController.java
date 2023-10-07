package com.dans.pro.mrbro.user.controller;

import com.dans.pro.mrbro.user.payload.request.LoginRequest;
import com.dans.pro.mrbro.user.payload.request.SignupRequest;
import com.dans.pro.mrbro.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        return new ResponseEntity<>(userService.authenticateUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        return new ResponseEntity<>(userService.registerUser(signUpRequest), HttpStatus.OK);
    }
}

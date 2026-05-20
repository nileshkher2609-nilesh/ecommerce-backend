package com.example.demoEcommmerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.example.demoEcommmerce.dto.UserRequest;
import com.example.demoEcommmerce.dto.UserResponse;
import com.example.demoEcommmerce.dto.auth.AuthRequest;
import com.example.demoEcommmerce.dto.auth.AuthResponse;
import com.example.demoEcommmerce.jwt.JwtUtil;
import com.example.demoEcommmerce.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserService userService;


    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}
	@PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token =
                jwtUtil.generateToken(request.getEmail());

        return ResponseEntity.ok(
                new AuthResponse(token)
        );
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
            @RequestBody UserRequest request) {

        UserResponse response = userService.addUser(request);

        return ResponseEntity.ok(response);
    }
}
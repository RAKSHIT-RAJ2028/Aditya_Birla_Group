package com.ABG.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class OAuth2Controller {
    
    @GetMapping("/login-success")
    public ResponseEntity<?> loginSuccess(OAuth2AuthenticationToken authentication) {
        // Extract user information from authentication
        String email = authentication.getPrincipal().getAttribute("email");
        String name = authentication.getPrincipal().getAttribute("name");
        
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "email", email,
            "name", name
        ));
    }

    @GetMapping("/login-failure")
    public ResponseEntity<?> loginFailure() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "OAuth2 login failed"));
    }
}
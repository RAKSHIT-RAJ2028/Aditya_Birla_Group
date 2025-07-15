package com.ABG.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ABG.model.LoginRequest;
import com.ABG.model.LoginResponse;
import com.ABG.model.RegisterRequest;
import com.ABG.model.User;
import com.ABG.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserServiceImpl userService;
	

    @Autowired
    private AuthenticationManager authenticationManager;
	
	
    // GET all users
	@GetMapping("/user")
	public ResponseEntity<List<User>> getAllUser()
	{
		List<User> users = userService.getAllUser();
		return new ResponseEntity<>(users , HttpStatus.OK);
	}
	
	 // Register new user
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
	    String role = request.getRole();

	    if (role == null || role.isBlank()) {
	        role = "USER";
	    }

	    if (!role.equalsIgnoreCase("ADMIN") && !role.equalsIgnoreCase("USER")) {
	        return ResponseEntity.badRequest().body("Invalid role. Allowed: ADMIN or USER");
	    }

	    String fullRole = "ROLE_" + role.toUpperCase();

	    userService.registerUser(request.getUsername(), request.getPassword(), fullRole);
	    return ResponseEntity.ok("User Registered Successfully with role " + fullRole);
	}


	  // Login endpoint
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	    try {

			User user = userService.findByUsernameOrEmail(loginRequest.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			
			if (user.getProvider() != null && !user.getProvider().equals("local")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of(
						"error", "Please login using " + user.getProvider(),
						"provider", user.getProvider()
					));
			}
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                loginRequest.getUsername(),
	                loginRequest.getPassword()
	            )
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        String role = authentication.getAuthorities().stream()
	            .findFirst()
	            .map(auth -> auth.getAuthority())
	            .orElse("ROLE_USER");

	        // Build and return a proper DTO response
	        LoginResponse response = new LoginResponse(
	            "Login successful",
	            loginRequest.getUsername(),
	            role
	        );

	        return ResponseEntity.ok(response);

	    } catch (AuthenticationException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Map.of("error", "Invalid username or password"));
	    }
	}

	//  @GetMapping("/oauth2/success")
    // public ResponseEntity<?> oauth2LoginSuccess(HttpServletRequest request) {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
    //     if (authentication != null && authentication.isAuthenticated()) {
    //         String username = authentication.getName();
    //         String role = authentication.getAuthorities().stream()
    //             .findFirst()
    //             .map(auth -> auth.getAuthority())
    //             .orElse("ROLE_USER");

    //         return ResponseEntity.ok(new LoginResponse(
    //             "OAuth2 login successful",
    //             username,
    //             role
    //         ));
    //     }
        
    //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    // }

	
	@GetMapping("/oauth2/success")
	public ResponseEntity<?> getOAuth2LoginInfo(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		// Extract user details
		String username = authentication.getName();
		String role = authentication.getAuthorities().stream()
			.findFirst()
			.map(GrantedAuthority::getAuthority)
			.orElse("ROLE_USER");
		
		// Return consistent user data structure
		return ResponseEntity.ok(Map.of(
			"username", username,
			"role", role,
			"authenticated", true
		));
	}

    @GetMapping("/oauth2/failure")
    public ResponseEntity<?> oauth2LoginFailure() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OAuth2 login failed");
    }

	
	
	
}

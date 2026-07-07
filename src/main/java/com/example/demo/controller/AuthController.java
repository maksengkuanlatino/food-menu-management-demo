package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	private IUserRepository userRepository;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
	    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
	        return ResponseEntity.badRequest().body("Username is already taken!");
	    }
	    userRepository.save(user);
	    return ResponseEntity.ok("Registration successful!");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User loginRequest) {
	    
	    User user = userRepository.findByUsername(loginRequest.getUsername())
	            .orElse(null);

	 
	    if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
	        return ResponseEntity.status(401).body("Invalid credentials");
	    }


	    String token = jwtUtil.generateToken(user.getUsername()); 
	    return ResponseEntity.ok(Map.of("token", token));
	}

}

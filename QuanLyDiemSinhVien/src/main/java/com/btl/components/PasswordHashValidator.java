package com.btl.components;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashValidator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Simulate saved hash (from your database)
        String encodedPassword = "$2a$10$UmGnTph3WQl.BzOAwKEbNu5vn9h6GJE3oRjWF6Q/UQVGPuH8Kl2Dm";  // Example hash
        
        // Raw password to validate
        String rawPassword = "e";  // Example password
        
        // Validate password
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("Password matches: " + matches);
    }
}

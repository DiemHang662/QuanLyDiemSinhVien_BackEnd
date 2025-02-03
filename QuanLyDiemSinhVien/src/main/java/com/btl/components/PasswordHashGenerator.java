package com.btl.components;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "gvu2";  // Example password
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
    }
}

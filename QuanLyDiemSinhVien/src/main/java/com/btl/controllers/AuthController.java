package com.btl.controllers;

import com.btl.components.JwtService;
import com.btl.pojo.NguoiDung;
import com.btl.pojo.SinhVien;
import com.btl.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody NguoiDung user) {
        // Authenticate the user
        if (nguoiDungService.authUser(user.getUsername(), user.getPassword())) {
            // Fetch user from the database
            NguoiDung foundUser = nguoiDungService.getUserByUsername(user.getUsername());

            // Check if the role matches
            if (foundUser != null && foundUser.getUserRole().equalsIgnoreCase(user.getUserRole())) {
                // Generate JWT token
                String token = jwtService.generateTokenLogin(user.getUsername());
                return new ResponseEntity<>(token, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid role", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<NguoiDung> getCurrentUser() {
        try {
            // Get the currently authenticated user's username
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            if (username == null || username.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            // Fetch the user details using the username
            NguoiDung currentUser = nguoiDungService.getUserByUsername(username);

            if (currentUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Return the current user details
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

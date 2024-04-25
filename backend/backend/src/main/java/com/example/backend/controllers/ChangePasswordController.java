package com.example.backend.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.backend.models.PasswordResetToken;
import com.example.backend.models.User;
import com.example.backend.payload.PasswordDto;
import com.example.backend.repositories.PasswordResetTokenRepository;
import com.example.backend.services.SecurityUserService;
import com.example.backend.services.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ChangePasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private MessageSource messages;

    @PostMapping("/user/savePassword")
    public ResponseEntity<?> savePassword(@RequestBody PasswordDto passwordDto) {
    	// Retrieve token from database based on token string
        PasswordResetToken token = tokenRepository.findByToken(passwordDto.getToken());
        if (token == null) {
            return new ResponseEntity<>("Error: Invalid token", HttpStatus.BAD_REQUEST);
        }

        // Retrieve user associated with the token
        User user = token.getUser();
        if (user == null || !user.getEmail().equals(passwordDto.getEmail())) {
            return new ResponseEntity<>("Error: No user found with email " + passwordDto.getEmail(), HttpStatus.NOT_FOUND);
        }

        // Validate the password reset token
        String result = securityUserService.validatePasswordResetToken(user.getId(), passwordDto.getToken());
        if (result != null) {
            return new ResponseEntity<>("Error: " + messages.getMessage("auth.message." + result, null, Locale.getDefault()), HttpStatus.BAD_REQUEST);
        }

        try {
            // Attempt to change user's password
            userService.changeUserPassword(user, passwordDto.getNewPassword());
            return ResponseEntity.ok("Password successfully updated");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            System.err.println("Error updating password: " + e.getMessage());
            return new ResponseEntity<>("Error updating password: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

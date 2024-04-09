package com.example.backend.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.models.User;
import com.example.backend.services.PasswordResetService;
import com.example.backend.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class PasswordResetController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordResetService passwordResetService;

	@PostMapping("/user/resetPassword")
	public String resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
		User user = userService.findUserByEmail(userEmail);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		String token = UUID.randomUUID().toString();
		passwordResetService.createPasswordResetTokenForUser(user, token);
		String appUrl = getAppUrl(request);
		passwordResetService.sendPasswordResetEmail(appUrl, request.getLocale(), token, user);

		return "We've sent an email to " + userEmail + " with further instructions to reset your password.";
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://localhost:8081";
	}
}

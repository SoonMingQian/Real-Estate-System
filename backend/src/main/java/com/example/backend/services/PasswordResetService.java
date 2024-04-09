package com.example.backend.services;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.example.backend.models.PasswordResetToken;
import com.example.backend.models.User;
import com.example.backend.repositories.PasswordResetTokenRepository;

@Service
public class PasswordResetService {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordResetTokenRepository tokenRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private MessageSource messages;

	@Autowired
	private Environment env;

	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken existingToken = tokenRepository.findByUser(user);
		if (existingToken != null) {
			tokenRepository.delete(existingToken);
		}
		PasswordResetToken myToken = new PasswordResetToken(user, token);
		// Set expiry date for 24 hours from now
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.HOUR, 24);
		myToken.setExpiryDate(new Date(cal.getTime().getTime()));
		tokenRepository.save(myToken);
	}

	public void sendPasswordResetEmail(String appUrl, Locale locale, String token, User user) {
		String url = appUrl + "/user/changePassword?token=" + token;
		String message = messages.getMessage("message.resetPassword", null, locale);
		SimpleMailMessage email = constructEmail("Reset Password", message + " \r\n" + url, user);
		emailService.sendEmail(email);
	}

	

	private SimpleMailMessage constructEmail(String subject, String body, User user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		return email;
	}
}

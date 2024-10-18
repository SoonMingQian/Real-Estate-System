package com.example.backend.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.models.PasswordResetToken;
import com.example.backend.repositories.PasswordResetTokenRepository;

@Service
public class SecurityUserService {

	@Autowired
	private PasswordResetTokenRepository tokenRepository;

	public String validatePasswordResetToken(Long userId, String token) {
		// Find the password reset token by token string
		PasswordResetToken passToken = tokenRepository.findByToken(token);

		// Check if the token is invalid or belongs to a different user
		if ((passToken == null) || (passToken.getUser().getId() != userId)) {
            return "invalidToken";
        }

		// Get the expiry date of the token
        Date expiryDate = passToken.getExpiryDate();
        if (expiryDate == null) {
            return "expiryDateNotFound";
        }
        
		// Convert the expiry date to LocalDateTime for comparison
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiryDateTime = expiryDate.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		// Check if the token has expired
		if (expiryDateTime.isBefore(now)) {
			return "expired";
		}
	
		// Token is valid
		return null;
	}
}

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
		PasswordResetToken passToken = tokenRepository.findByToken(token);

		if ((passToken == null) || (passToken.getUser().getId() != userId)) {
            return "invalidToken";
        }

        Date expiryDate = passToken.getExpiryDate();
        if (expiryDate == null) {
            return "expiryDateNotFound";
        }
        
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiryDateTime = expiryDate.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		if (expiryDateTime.isBefore(now)) {
			return "expired";
		}
	
		return null;
	}
}

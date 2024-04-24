package com.example.backend.repositories;

import java.sql.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.backend.models.PasswordResetToken;
import com.example.backend.models.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);
	
	PasswordResetToken findByUser(User user);
	
}

package com.example.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	Optional<User> findOptionalByEmail(String email);
	 
	Optional<User> findById(Long id);

	Boolean existsByEmail(String email);
	
	
}

package com.example.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.models.Property;
import com.example.backend.models.Status;
import com.example.backend.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	Optional<User> findOptionalByEmail(String email);
	 
	Optional<User> findById(Long id);

	Boolean existsByEmail(String email);
	
	List<User> findByStatus(Status status);
	
	
}

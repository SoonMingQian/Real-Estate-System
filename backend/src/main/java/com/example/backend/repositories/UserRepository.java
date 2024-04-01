package com.example.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	
	Boolean existsByEmail(String email);
	
}

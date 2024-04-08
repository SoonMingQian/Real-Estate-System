package com.example.backend.controllers;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.models.Property;
import com.example.backend.models.User;
import com.example.backend.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/useraccount/{id}")
	public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id){
		Optional<User> user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/updateuser/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id){
		User updateUser = userService.updateUser(id, user);
		return ResponseEntity.ok(updateUser);
	}
	
	@PostMapping("/users/shortlist/{userId}/{propertyId}")
	public User shortlistProperty(@PathVariable Long userId, @PathVariable Long propertyId) {
		return userService.shortlistProperty(userId, propertyId);
	}
	
	@GetMapping("/shortlist/{userId}")
	public ResponseEntity<Set<Property>> getShortlistedProperties(@PathVariable Long userId) {
		  Set<Property> shortlistedProperties = userService.getShortlistedProperties(userId);
	      return ResponseEntity.ok(shortlistedProperties);
	}
	
	@DeleteMapping("/deleteshortlist/{userId}/{propertyId}")
	public ResponseEntity<Void> deleteShortlist(@PathVariable Long userId, @PathVariable Long propertyId) {
        userService.removeShortlistedProperty(userId, propertyId);
        return ResponseEntity.noContent().build();
    }
	
	@PostMapping("/user/resetPassword")
	public GenericResponse resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
		Optional<User> user = userService.findUserByEmail(userEmail);
		if(user == null) {
			throw new UsernameNotFoundException(userEmail);
		}
		String token = UUID.randomUUID().toString();
		userService.
	}
	
}


package com.example.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.models.PasswordResetToken;
import com.example.backend.models.Property;
import com.example.backend.models.Status;
import com.example.backend.models.User;
import com.example.backend.repositories.PasswordResetTokenRepository;
import com.example.backend.repositories.PropertyRepository;
import com.example.backend.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PropertyRepository propertyRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	PropertyService propertyService;

	public Optional<User> getUserById(Long userId) {
		return userRepository.findById(userId);
	}

	public User updateUser(Long id, User newUser) {
		return userRepository.findById(id)
				.map(user -> {
					user.setFirstName(newUser.getFirstName());
					user.setLastName(newUser.getLastName());
					user.setNationality(newUser.getNationality());
					user.setAddress(newUser.getAddress());
					user.setEircode(newUser.getEircode());
					return userRepository.save(user);
				})
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id" + id));
	}

	public User shortlistProperty(Long userId, Long propertyId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id" + userId));
		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new ResourceNotFoundException("Property not found with id" + propertyId));

		user.getShortlistedProperties().add(property);
		return userRepository.save(user);
	}

	public Set<Property> getShortlistedProperties(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
		return user.getShortlistedProperties();
	}

	public void removeShortlistedProperty(Long userId, Long propertyId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new ResourceNotFoundException("Property not found with id " + propertyId));

		user.getShortlistedProperties().remove(property);

		userRepository.save(user);

	}

	public void removePropertyFromShortlist(Long proprtyId) {
		List<User> users = userRepository.findAll();
		for (User user : users) {
			Set<Property> shortlistedProperties = user.getShortlistedProperties();
			shortlistedProperties.removeIf(property -> property.getId().equals(proprtyId));
			user.setShortlistedProperties(shortlistedProperties);
			userRepository.save(user);
		}
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void changeUserPassword(User user, String password) {
		String encodedPassword = passwordEncoder.encode(password);
		user.setPassword(encodedPassword);
		userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public void deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		PasswordResetToken token = passwordResetTokenRepository.findByUser(user);
		if (token != null) {
			passwordResetTokenRepository.delete(token);
		}
		userRepository.deleteById(id);
	}
	
	public List<User> getStatusPending(){
		return userRepository.findByStatus(Status.PENDING);
	}
	
	public User approveUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id"));
		user.setStatus(Status.APPROVED);
	    return userRepository.save(user);
	}
	
	public User rejectUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id"));
		user.setStatus(Status.REJECTED);
	    return userRepository.save(user);
	}
	

}

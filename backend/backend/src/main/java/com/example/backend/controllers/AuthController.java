package com.example.backend.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.models.ERole;
import com.example.backend.models.Role;
import com.example.backend.models.Status;
import com.example.backend.models.User;
import com.example.backend.payload.LoginRequest;
import com.example.backend.payload.SignupRequest;
import com.example.backend.payload.response.JwtResponse;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repositories.RoleRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.security.jwt.JwtUtils;
import com.example.backend.security.services.UserDetailsImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
/**
 * AuthController is a class responsible for handling authentication-related operations.
 * This includes operations such as login, registration, and password reset.
 * It interacts with the authentication manager and user service to perform these operations.
 */
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		//Authentication process
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		UserDetailsImpl userDetailss = (UserDetailsImpl) authentication.getPrincipal();

		//Check user status
		Optional<User> optionalUser = userRepository.findOptionalByEmail(userDetailss.getEmail());
		if (!optionalUser.isPresent()) {
			throw new RuntimeException("Error: User not found.");
		}

		User user = optionalUser.get();
		if (user.getStatus() == Status.PENDING || user.getStatus() == Status.REJECTED) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Your account is pending approval or rejected"));
		}
		//Set authentication in SecurityContextHolder
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		//Get user details and roles
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		//Respond with JWT and user details
		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getEmail(),
				roles));

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		//Check if email already exists
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		//Create new user's account
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getFirstName(),
				signUpRequest.getLastName(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		System.out.println(strRoles);
		Set<Role> roles = new HashSet<>();

		//Assign roles baseed on signup request
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "agent":
						Role modRole = roleRepository.findByName(ERole.ROLE_AGENT)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);
						user.setStatus(Status.PENDING);

						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}

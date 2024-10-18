package com.example.backend.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.backend.models.ERole;
import com.example.backend.models.Role;
import com.example.backend.repositories.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void run(String... args) throws Exception {
		if(roleRepository.count() == 0) {
			roleRepository.save(new Role(ERole.ROLE_USER));
            roleRepository.save(new Role(ERole.ROLE_AGENT));
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
		}
		
	}

}

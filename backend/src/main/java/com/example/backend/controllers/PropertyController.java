package com.example.backend.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.models.Facility;
import com.example.backend.models.Property;
import com.example.backend.repositories.FacilityRepository;
import com.example.backend.repositories.PropertyRepository;

@RestController
public class PropertyController {

	@Autowired
	PropertyRepository propertyRepository;

	@Autowired
	FacilityRepository facilityRepository;

	@PostMapping("/add-property")
	public ResponseEntity<Property> createProperty(@RequestBody Property property) {
		Property newProperty = new Property(property.getPropertyName(),
            property.getPropertyAddress(),
            property.getSaleType(),
            property.getPrice(),
            property.getSqft(),
            property.getNumOfBath(),
            property.getNumOfBed(),
            property.getDescription());

			Set<Facility> facilities = new HashSet<>();
			for(Facility facility : property.getFacilities()){
				Optional<Facility> existingFacilityOptional = facilityRepository.findById(facility.getId());
				if (existingFacilityOptional.isPresent()) {
					facilities.add(existingFacilityOptional.get());
				}
			}
			property.setFacilities(facilities);
			Property savedProperty = propertyRepository.save(property);
			return new ResponseEntity<>(savedProperty, HttpStatus.CREATED);
	}
}



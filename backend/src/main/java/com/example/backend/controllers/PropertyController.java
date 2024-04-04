package com.example.backend.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.models.Facility;
import com.example.backend.models.File;
import com.example.backend.models.Property;
import com.example.backend.models.User;
import com.example.backend.repositories.FacilityRepository;
import com.example.backend.repositories.PropertyRepository;
import com.example.backend.services.FacilityService;
import com.example.backend.services.FileService;
import com.example.backend.services.PropertyService;
import com.example.backend.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PropertyController {

	@Autowired
	PropertyService propertyService;

	@Autowired
	PropertyRepository propertyRepository;

	@Autowired
	FacilityRepository facilityRepository;

	@Autowired
	FileService fileService;
	
	@Autowired
	FacilityService facilityService;
	
	@Autowired
	UserService userService;

	@GetMapping("/properties/sale")
	public ResponseEntity<List<Property>> getAllSaleProperties() {
	    List<Property> properties = propertyService.getSaleProperties();
	    return new ResponseEntity<>(properties, HttpStatus.OK);
	}
	
	@GetMapping("/properties/rent")
	public ResponseEntity<List<Property>> getAllRentProperties() {
	    List<Property> properties = propertyService.getRentProperties();
	    return new ResponseEntity<>(properties, HttpStatus.OK);
	}
	
	@GetMapping("/apartment")
	public ResponseEntity<List<Property>> getAllApartment() {
	    List<Property> properties = propertyService.getApartment();
	    return new ResponseEntity<>(properties, HttpStatus.OK);
	}	
	
	@GetMapping("/office")
	public ResponseEntity<List<Property>> getAllOffice() {
	    List<Property> properties = propertyService.getOffice();
	    return new ResponseEntity<>(properties, HttpStatus.OK);
	}
	
	@GetMapping("property/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        Optional<Property> property = propertyService.getPropertyById(id);
        if (property.isPresent()) {
            return ResponseEntity.ok(property.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Property>> getPropertiesByUserId(@PathVariable Long userId){
		List<Property> properties = propertyService.getPropertiesByUserId(userId);
		return new ResponseEntity<>(properties, HttpStatus.OK);
	}

	@PostMapping("/add-property/{userId}")
	@PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<?> createProperty(@RequestBody Property property, @PathVariable Long userId) {
		Optional<User> userOptional = userService.getUserById(userId);
		if (!userOptional.isPresent()) {
		    Map<String, Object> errorResponse = new HashMap<>();
		    errorResponse.put("error", "User not found");
		    errorResponse.put("userId", userId);
		    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
		User user = userOptional.get();
		Property newProperty = new Property(property.getPropertyName(),
				property.getPropertyAddress(),
				property.getSaleType(),
				property.getPropertyType(),
				property.getPrice(),
				property.getSqft(),
				property.getNumOfBath(),
				property.getNumOfBed(),
				property.getDescription());
		
		newProperty.setUser(user);

		Set<Facility> facilities = new HashSet<>();
		for (Facility facility : property.getFacilities()) {
			Optional<Facility> existingFacilityOptional = facilityRepository.findById(facility.getId());
			if (existingFacilityOptional.isPresent()) {
				facilities.add(existingFacilityOptional.get());
			}
		}
		newProperty.setFacilities(facilities);
		Property savedProperty = propertyRepository.save(newProperty);
		return new ResponseEntity<>(savedProperty, HttpStatus.CREATED);

	}
	
	@PutMapping("/updateproperty/{id}")
	@PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<Property> updateProperty(@PathVariable(value = "id") Long propertyId,
													@RequestBody Property propertyDetails){
		Property updatedProperty = propertyService.updateProperty(propertyId, propertyDetails);
		return ResponseEntity.ok(updatedProperty);
	}
	
	@DeleteMapping("/deleteproperty/{propertyId}")
	@PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<String> deleteProperty(@PathVariable Long propertyId){
	    try {
	        propertyService.deleteFacilitiesByPropertyId(propertyId);
			propertyService.deleteFilesByPropertyId(propertyId);
	        propertyService.deleteProperty(propertyId);
	        return ResponseEntity.ok("Property deleted successfully");
	    } catch(Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete property: " + e.getMessage());
	    }
	}
	
	@GetMapping("/filter")
	public List<Property> getFilteredProperties(
			@RequestParam(required = false) String propertyType,
			@RequestParam(required = false) Integer minPrice,
			@RequestParam(required = false) Integer maxPrice,
			@RequestParam(required = false) Integer minNumOfBed,
			@RequestParam(required = false) Integer maxNumOfBed,
			@RequestParam(required = false) Integer minNumOfBath,
			@RequestParam(required = false) Integer maxNumOfBath,
			@RequestParam(required = false) String saleType,
			@RequestParam(required = false) Long facilityId){
		return propertyService.getFilteredProperties(propertyType, minPrice, maxPrice, minNumOfBed, maxNumOfBed, minNumOfBath, maxNumOfBath, saleType, facilityId);
	}
}

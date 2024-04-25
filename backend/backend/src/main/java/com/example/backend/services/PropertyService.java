package com.example.backend.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.models.Facility;
import com.example.backend.models.File;
import com.example.backend.models.Property;
import com.example.backend.models.Status;
import com.example.backend.models.User;
import com.example.backend.repositories.FacilityRepository;
import com.example.backend.repositories.FileRepository;
import com.example.backend.repositories.PropertyRepository;

@Service
public class PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
    private FacilityRepository facilityRepository;

	@Autowired
	private FileRepository fileRepository;
	
	public Property saveProperty(Property property) {
		return propertyRepository.save(property);
	}
	
	public List<Property> getSaleProperties(){
		return propertyRepository.findByStatusAndSaleType(Status.APPROVED, "sale");
	}
	
	public List<Property> getRentProperties(){
		return propertyRepository.findByStatusAndSaleType(Status.APPROVED, "rent");
	}
	
	public List<Property> getStatusPending(){
		return propertyRepository.findByStatus(Status.PENDING);
	}
	
	public Optional<Property> getPropertyById(Long id) {
	    return propertyRepository.findById(id);
	}
	
	public String getUserEmail(Long id) {
	    Optional<Property> property = propertyRepository.findById(id);
	    if (property.isPresent()) {
	        User user = property.get().getUser();
	        return user != null ? user.getEmail() : null;
	    }
	    return null;
	}
	
	public List<Property> getApartment(){
		return propertyRepository.findByStatusAndPropertyType(Status.APPROVED, "apartment");
	}
	
	public List<Property> getOffice(){
		return propertyRepository.findByStatusAndPropertyType(Status.APPROVED, "office");
	}
	
	public List<Property> getPropertiesByUserId(Long userId){
		return propertyRepository.findByUserId(userId);
	}
	
	public Property updateProperty(Long id, Property propertyDetails) {
		Property property = propertyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Property not found for this id"));
		
		property.setPropertyName(propertyDetails.getPropertyName());
		property.setPropertyAddress(propertyDetails.getPropertyAddress());
		property.setPrice(propertyDetails.getPrice());
		property.setNumOfBed(propertyDetails.getNumOfBed());
		property.setNumOfBath(propertyDetails.getNumOfBath());
		property.setSqft(propertyDetails.getSqft());
		property.setDescription(propertyDetails.getDescription());
		property.setFiles(propertyDetails.getFiles());
		property.setFacilities(propertyDetails.getFacilities());
		
		return propertyRepository.save(property);
	}
	
	// Delete facilities associated with a property by its ID
	public void deleteFacilitiesByPropertyId(Long propertyId) {
		Optional<Property> property = propertyRepository.findById(propertyId);
        if (property.isPresent()) {
            Set<Facility> facilities = property.get().getFacilities();
            property.get().getFacilities().clear();
            for (Facility facility : facilities) {
                facility.getProperties().remove(property.get());
                facilityRepository.save(facility);
            }
            propertyRepository.save(property.get());
        } else {
            throw new NoSuchElementException("No property found with id " + propertyId);
        }
    }
	
	public void deleteProperty(Long propertyId) {
	    propertyRepository.deleteById(propertyId);
	}

	// Delete files associated with a property by its ID
	public void deleteFilesByPropertyId(Long propertyId) {
		List<File> files = fileRepository.findByPropertyId(propertyId);
		fileRepository.deleteAll(files);
	}
	
	// Get filtered properties based on various criteria
	public List<Property> getFilteredProperties(String propertyType, Integer minPrice, Integer maxPrice, Integer minNumOfBed, Integer maxNumOfBed, Integer minNumOfBath, Integer maxNumOfBath, String saleType, Long facilityId){
		return propertyRepository.findWithFilters(propertyType, minPrice, maxPrice, minNumOfBed, maxNumOfBed, minNumOfBath, maxNumOfBath, saleType, facilityId);
	}
	
	public List<Property> getAllProperties() {
		return propertyRepository.findAll();
	}
	
	public Property approveProperty(Long id) {
		Property property = propertyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Property not found for this id"));
		property.setStatus(Status.APPROVED);
	    return propertyRepository.save(property);
	}
	
	public Property rejectProperty(Long id) {
		Property property = propertyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Property not found for this id"));
		property.setStatus(Status.REJECTED);
	    return propertyRepository.save(property);
	}
	
	public Map<String, Long> getPropertyCounts(){
		Map<String, Long> counts = new HashMap<>();
		counts.put("rent", propertyRepository.countBySaleTypeAndStatus("rent", Status.APPROVED));
		counts.put("sale", propertyRepository.countBySaleTypeAndStatus("sale", Status.APPROVED));
		return counts;
	}
	
	public Map<String, Long> getPropertyTypeCounts(){
		Map<String, Long> counts = new HashMap<>();
		counts.put("house", propertyRepository.countByPropertyTypeAndStatus("house", Status.APPROVED));
		counts.put("apartment", propertyRepository.countByPropertyTypeAndStatus("apartment", Status.APPROVED));
		counts.put("office", propertyRepository.countByPropertyTypeAndStatus("office", Status.APPROVED));
		counts.put("parking", propertyRepository.countByPropertyTypeAndStatus("parking", Status.APPROVED));
		counts.put("restaurant/bar", propertyRepository.countByPropertyTypeAndStatus("restaurant/bar", Status.APPROVED));
		return counts;
	}
}

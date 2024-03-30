package com.example.backend.services;

import java.util.ArrayList;
import java.util.List;
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
		return propertyRepository.findByStatusAndSaleType(Status.PENDING, "sale");
	}
	
	public List<Property> getRentProperties(){
		return propertyRepository.findByStatusAndSaleType(Status.PENDING, "rent");
	}
	
	public Optional<Property> getPropertyById(Long id) {
	    return propertyRepository.findById(id);
	}
	
	public List<Property> getApartment(){
		return propertyRepository.findByPropertyType("apartment");
	}
	
	public List<Property> getOffice(){
		return propertyRepository.findByPropertyType("office");
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

	public void deleteFilesByPropertyId(Long propertyId) {
		List<File> files = fileRepository.findByPropertyId(propertyId);
		fileRepository.deleteAll(files);
	}
	
	public List<Property> getFilteredProperties(String propertyType, Integer minPrice, Integer maxPrice, Integer minNumOfBed, Integer maxNumOfBed, Integer minNumOfBath, Integer maxNumOfBath, String saleType, Long facility_id){
		return propertyRepository.findWithFilters(propertyType, minPrice, maxPrice, minNumOfBed, maxNumOfBed, minNumOfBath, maxNumOfBath, saleType, facility_id);
	}
}

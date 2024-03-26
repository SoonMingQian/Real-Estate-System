package com.example.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.models.Facility;
import com.example.backend.models.File;
import com.example.backend.models.Property;
import com.example.backend.models.Status;
import com.example.backend.repositories.FileRepository;
import com.example.backend.repositories.PropertyRepository;

@Service
public class PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;
	
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
	
	
	
}

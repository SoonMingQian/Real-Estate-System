package com.example.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.models.Facility;
import com.example.backend.repositories.FacilityRepository;

@Service
public class FacilityService {

	@Autowired
	private FacilityRepository facilityRepository;
	
	public Facility saveFacility(Facility facility) {
		return facilityRepository.save(facility);
	}
}

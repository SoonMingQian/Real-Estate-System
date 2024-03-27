package com.example.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.models.Property;
import com.example.backend.models.Status;

import java.util.List;
import java.util.Optional;


@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
	List<Property> findByStatusAndSaleType(Status status, String saleType);
	
	Optional<Property> findById(Long id);
	
	List<Property> findByPropertyType(String propertyType);
}

package com.example.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.models.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
	
	List<File> findByPropertyId(Long postId);
	
	@Query("SELECT f FROM File f WHERE f.property.id = :propertyId")
	List<File> findFilesByPropertyId(@Param("propertyId") Long propertyId);
	
	Optional<File> findById(Long id);
}

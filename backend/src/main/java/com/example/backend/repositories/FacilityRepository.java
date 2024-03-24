package com.example.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.models.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer> {
	Optional<Facility> findById(Integer id);
}

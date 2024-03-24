package com.example.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.models.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

}
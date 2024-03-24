package com.example.backend.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Facility {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	private String name;
	
	@ManyToMany(mappedBy = "facilities")
    private Set<Property> properties = new HashSet<>();

    
	public Facility() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<Property> getProperties() {
		return properties;
	}


	public void setProperties(Set<Property> properties) {
		this.properties = properties;
	}
	
	
}

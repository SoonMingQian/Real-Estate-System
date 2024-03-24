package com.example.backend.models;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Property {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String propertyName;
	
	private String propertyAddress;
	
	private String saleType;
	 
	private Status status = Status.PENDING;
	
	private double price;
	
	private double sqft;
	
	private int numOfBath;
	
	private int numOfBed;
	
	private String description;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "property_facility",
	           joinColumns = @JoinColumn(name = "property_id"),
	           inverseJoinColumns = @JoinColumn(name = "facility_id"))
	private Set<Facility> facilities = new HashSet<>();

	public Property() {
		super();
	}

	
	public Property(String propertyName, String propertyAddress, String saleType, double price, double sqft,
			int numOfBath, int numOfBed, String description) {
		super();
		this.propertyName = propertyName;
		this.propertyAddress = propertyAddress;
		this.saleType = saleType;
		this.price = price;
		this.sqft = sqft;
		this.numOfBath = numOfBath;
		this.numOfBed = numOfBed;
		this.description = description;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public String getSaleType() {
		return saleType;
	}


	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSqft() {
		return sqft;
	}

	public void setSqft(double sqft) {
		this.sqft = sqft;
	}

	public int getNumOfBath() {
		return numOfBath;
	}

	public void setNumOfBath(int numOfBath) {
		this.numOfBath = numOfBath;
	}

	public int getNumOfBed() {
		return numOfBed;
	}

	public void setNumOfBed(int numOfBed) {
		this.numOfBed = numOfBed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Set<Facility> getFacilities() {
		return facilities;
	}


	public void setFacilities(Set<Facility> facilities) {
		this.facilities = facilities;
	}



	
}

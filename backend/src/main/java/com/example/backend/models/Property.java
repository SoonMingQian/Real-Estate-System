package com.example.backend.models;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Property {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String propertyName;
	
	private String propertyAddress;
	
	private String saleType;
	
	private String propertyType;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.PENDING;
	
	private double price;
	
	private double sqft;
	
	private int numOfBath;
	
	private int numOfBed;
	
	private String description;
	
	@OneToMany(mappedBy = "property")
	private List<File> files;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "property_facility",
	           joinColumns = @JoinColumn(name = "property_id"),
	           inverseJoinColumns = @JoinColumn(name = "facility_id"))
	private Set<Facility> facilities = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore 
	private User user;
	
	@ManyToMany(mappedBy = "shortlistedProperties")
	@JsonIgnore 
	private Set<User> usersWhoShortlisted = new HashSet<>();

	public Property() {
		super();
	}

	
	public Property(String propertyName, String propertyAddress, String saleType, String propertyType, double price, double sqft,
			int numOfBath, int numOfBed, String description) {
		super();
		this.propertyName = propertyName;
		this.propertyAddress = propertyAddress;
		this.saleType = saleType;
		this.propertyType = propertyType;
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


	public List<File> getFiles() {
		return files;
	}


	public void setFiles(List<File> files) {
		this.files = files;
	}


	public String getPropertyType() {
		return propertyType;
	}


	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Set<User> getUsersWhoShortlisted() {
		return usersWhoShortlisted;
	}


	public void setUsersWhoShortlisted(Set<User> usersWhoShortlisted) {
		this.usersWhoShortlisted = usersWhoShortlisted;
	}


		
}

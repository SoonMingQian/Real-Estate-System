package com.example.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.models.Property;
import com.example.backend.models.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
	List<Property> findByStatusAndSaleType(Status status, String saleType);
	
	List<Property> findByStatus(Status status);

	Optional<Property> findById(Long id);

	List<Property> findByStatusAndPropertyType(Status status, String propertyType);

	@Query("SELECT p FROM Property p JOIN p.facilities f WHERE" +
        "(p.propertyType = :propertyType OR :propertyType IS NULL) AND " +
        "(p.price >= :minPrice OR :minPrice IS NULL) AND " +
        "(p.price <= :maxPrice OR :maxPrice IS NULL) AND " +
        "(p.numOfBed >= :minNumOfBed OR :minNumOfBed IS NULL) AND " +
        "(p.numOfBed <= :maxNumOfBed OR :maxNumOfBed IS NULL) AND " +
        "(p.numOfBath >= :minNumOfBath OR :minNumOfBath IS NULL) AND " +
        "(p.numOfBath <= :maxNumOfBath OR :maxNumOfBath IS NULL) AND " +
        "(p.saleType = :saleType OR :saleType IS NULL) AND " +
        "(:facilityId IS NULL OR f.id = :facilityId) AND " +
        "(p.status = 'APPROVED')")
	List<Property> findWithFilters(
	        @Param("propertyType") String propertyType,
	        @Param("minPrice") Integer minPrice,
	        @Param("maxPrice") Integer maxPrice,
	        @Param("minNumOfBed") Integer minNumOfBed,
	        @Param("maxNumOfBed") Integer maxNumOfBed,
	        @Param("minNumOfBath") Integer minNumOfBath,
	        @Param("maxNumOfBath") Integer maxNumOfBath,
	        @Param("saleType") String saleType,
	        @Param("facilityId") Long facilityId);
	
	List<Property> findByUserId(Long userId);
	
	Long countBySaleTypeAndStatus(String saleType, Status status);
	
	Long countByPropertyTypeAndStatus(String propertyType, Status status);
}

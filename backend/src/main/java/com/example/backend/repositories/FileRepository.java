package com.example.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.models.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
	
	List<File> findByPropertyId(Long postId);
}

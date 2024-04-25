package com.example.backend.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.models.File;
import com.example.backend.models.Property;
import com.example.backend.repositories.FileRepository;
import com.example.backend.repositories.PropertyRepository;

@Service
public class FileService {
	
	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private FileRepository fileRepository;
	
	public File saveFile(Long propertyId, String fileName, String contentType, Long fileSize, byte[] fileData) {
		File file = new File();
		file.setFileName(fileName);
		file.setContentType(contentType);
		file.setFileSize(fileSize);
		file.setFileData(fileData);
		Property property = propertyRepository.findById(propertyId)
			    .orElseThrow(() -> new ResourceNotFoundException("Not found Property with id = " + propertyId));
		file.setProperty(property);
		return fileRepository.save(file);
	}
	
	// Get the first file associated with a property by its ID
	public File getFirstFileByPropertyId(Long propertyId) {
	    List<File> files = fileRepository.findFilesByPropertyId(propertyId);
	    if (files.isEmpty()) {
	        throw new ResourceNotFoundException("No files found for Property with id = " + propertyId);
	    }
	    return files.get(0);
	}
	
	// Delete a file by its ID
	public void deleteFile(Long fileId) {
		Optional<File> fileOptional = fileRepository.findById(fileId);
		if(!fileOptional.isPresent()) {
			throw new IllegalArgumentException("File not found" + fileId);
		}
		// Delete the file from the database
		fileRepository.delete(fileOptional.get());
	}

}

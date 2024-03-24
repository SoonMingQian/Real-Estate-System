package com.example.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

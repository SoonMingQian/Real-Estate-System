package com.example.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.models.File;
import com.example.backend.repositories.PropertyRepository;
import com.example.backend.services.FileService;

@CrossOrigin(origins = "*")
@RestController
public class FileController {

	@Autowired
	private FileService fileService;
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	private static int fileUploadCount = 0;

	@PostMapping("/add-property/{propertyId}/upload")
	public ResponseEntity<String> uploadFiles(@PathVariable("propertyId") Long propertyId, @RequestParam("files") MultipartFile[] files){
		try {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					byte[] fileData = file.getBytes();
					
					String fileName = file.getOriginalFilename();
					String contentType = file.getContentType();
					Long fileSize = file.getSize();
					
					
					File savedFile = fileService.saveFile(propertyId, fileName, contentType, fileSize, fileData);
					fileUploadCount++;

					long fileSizeKB = file.getSize() / 1024;

					// Print file info for each file
					logger.info(fileUploadCount + " File uploaded | File Size: " + fileSizeKB + " KB | File Name: "
							+ file.getOriginalFilename());
				}
			}
			
			logger.info(fileUploadCount + " Files uploaded Successfully.");

			String message = fileUploadCount + " Files Uploaded";

			return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
		}catch (Exception e) {
			logger.info("Files not uploaded !! Try Again !!");
			String errorMessage = "File upload failed: " + e.getMessage();
			logger.error(errorMessage, e);
			return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
}

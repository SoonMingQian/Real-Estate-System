package com.example.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.models.File;
import com.example.backend.repositories.PropertyRepository;
import com.example.backend.services.FileService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class FileController {

	@Autowired
	private FileService fileService;
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	// Logger for logging file upload information
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	// Counter to keep track of file uploads
	private static int fileUploadCount = 0;

	// Endpoint to retrieve the first file by property ID
	@GetMapping("/properties/{propertyId}/file")
	public ResponseEntity<byte[]> getFirstFileByPropertyId(@PathVariable Long propertyId) {
	    File file = fileService.getFirstFileByPropertyId(propertyId);
	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(file.getContentType()))
	            .body(file.getFileData());
	}
	
	// Endpoint to delete a file by file ID, accessible only to users with 'AGENT' role
	@DeleteMapping("/file/{fileId}")
	@PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<String> deleteFile(@PathVariable Long fileId){
		try {
			fileService.deleteFile(fileId);
			return ResponseEntity.ok("File deleted successfully");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete file: " + e.getMessage());
		}
	}

	// Endpoint to upload files for a property, accessible only to users with 'AGENT' role
	@PostMapping("/add-property/{propertyId}/upload")
	@PreAuthorize("hasRole('AGENT')")
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

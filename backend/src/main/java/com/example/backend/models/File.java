package com.example.backend.models;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String fileName;
	
	private String contentType;
	
	private Long fileSize;
	
	@Lob
	@Column(columnDefinition="MEDIUMBLOB")
	private byte[] fileData;
	
	@ManyToOne
	@JoinColumn(name = "propertyId")
    @JsonIgnore 
	private Property property;

	public File() {
		super();
	}

	public File(Long id, String fileName, String contentType, Long fileSize, byte[] fileData, Property property) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.fileData = fileData;
		this.property = property;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	@Override
	public String toString() {
		return "File [id=" + id + ", fileName=" + fileName + ", contentType=" + contentType + ", fileSize=" + fileSize
				+ ", fileData=" + Arrays.toString(fileData) + ", property=" + property + "]";
	}
	
	
}

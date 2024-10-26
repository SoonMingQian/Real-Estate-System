# Use a base image with Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Set working directory in container
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the JAR file
RUN mvn clean package

# Create the final image with the JAR
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy only the JAR from the previous build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/real-estate.jar

# Command to run the JAR
CMD ["java", "-jar", "/app/real-estate.jar"]

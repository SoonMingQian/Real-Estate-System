# Use a base image with Maven
FROM maven:3.8.4-openjdk-21 AS build

# Set working directory in container
WORKDIR /app

# Copy the pom.xml and source code
COPY backend/pom.xml .
COPY backend/src ./src

# Build the JAR file
RUN mvn clean package

# Create the final image with the JAR
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy only the JAR from the previous build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/real-estate.jar

# Command to run the JAR
CMD ["java", "-jar", "/app/real-estate.jar"]

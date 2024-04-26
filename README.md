# Estate Eagle Web Application

## Overview
The Estate Eagle web application is a comprehensive solution designed to streamline the process of finding and listing properties in the real estate market. Built with Vue.js for the frontend, SpringBoot for the backend, and MySQL for data storage, this application offers a user-friendly interface, robust backend services, and efficient data management capabilities.

## Installation

1. Clone the repository: `git clone https://github.com/yourusername/estate-eagle.git`
2. Navigate to the project directory: `cd estate-eagle`
3. Install dependencies:
   - Backend: Dependencies are managed via Maven and should be resolved automatically.
4. Install Vue.js
5. Insert below query into database
   INSERT INTO facility (name) VALUES
   ('Air-Conditioning'),
   ('Balcony'),
   ('WIFI'),
   ('Washing Machine / Dryer'),
   ('Oven / Microwave'),
   ('Water Heater'),
   ('Fridge'),
   ('Furnished');

   &&

   INSERT INTO roles(name) VALUES('ROLE_USER');
   INSERT INTO roles(name) VALUES('ROLE_AGENT');
   INSERT INTO roles(name) VALUES('ROLE_ADMIN');

## Configuration

- **Backend**:
  - Configure database connection settings in `application.properties`.
  - Customize security configurations in `SecurityConfig.java`.

## Usage

1. Start the backend server: `mvn spring-boot:run` (or deploy the WAR file to a servlet container).
2. Start the frontend: npm run serve


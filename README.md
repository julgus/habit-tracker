# Habit Tracker API
This project is an example solution for the Habit Tracker API assignment, which tasks participants in Netlight's Tech Avenue with designing and implementing a fully functional backend service. 
The API is built with Spring Boot, uses Liquibase for database migrations, and relies on a MariaDB test container for storage. It allows users to track habits, log progress, and gain insights into behavioral patterns, helping to establish goals and enhance productivity.

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Database Migrations with Liquibase](#database-migrations-with-liquibase)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Example Usage](#example-usage)
- [Troubleshooting](#troubleshooting)
- [License](#license)
- [Contributors](#contributors)


## Tech Stack
This project leverages the following technologies to deliver a robust and scalable habit-tracking API:

- Spring Boot: Provides a streamlined framework for building the RESTful API with minimal configuration.
- Liquibase: Handles database versioning and migrations, ensuring consistency across environments.
- MariaDB: A reliable and high-performance database for storing habit and tracking data.
- JUnit 5 & Mockito: Used for unit and integration testing to ensure application reliability.
- Docker: Runs the database in an isolated containerized environment.
- Java 17

## Project Structure
The project follows a clean, modular structure adhering to industry best practices for Spring Boot applications. Below is an overview of the key directories and files:

```plaintext
habit-tracker/
├── src/main/java/com/example/habittracker/
│   ├── controller/            # REST Controllers for API Endpoints
│   ├── model/                 # Entities (Habit, Tracking)
│   ├── exception/             # Custom exceptions
│   ├── mapper/                # Mappings for habit responses
│   ├── dto/                   # Data Transfer objects
│        ├── request/          # Request objects
│        ├── response/         # Response objects
│   ├── repository/            # JPA Repositories 
│   ├── service/               # Service layer with business logic
│   └── HabitTrackerApplication.java  # Main Spring Boot application
├── src/main/resources/
│   ├── application.yml        # Configuration for database, server, etc.
│   ├── db/changelog/          # Liquibase migrations, defines DB schema
│   └── db.changelog-master.xml # Liquibase master changelog
├── docker-compose.yml         # Docker configuration for MariaDB
└── README.md                  # Project documentation
```

## Running the database in Docker 
To set up and run the MariaDB database required for this project using Docker, follow these steps:

### 1. Ensure Docker is installed
Make sure Docker is installed and running on your system. You can download Docker from the official site.

### 2. Pull the MariaDB Docker Image
Use the following command to start the MariaDB container using Docker Compose: 

```java
docker compose up -d 
```
The first time you start the database, Docker will automatically download all the required MariaDB files and then start the application.
If all works as intended, you should end up seeing the following message: 

```text
[+] Building 0.0s (0/0)                                                                                                                                                            docker:desktop-linux
[+] Running 1/1
 ✔ Container habit-tracker-mariadb-1  Started  
```

## Running the application 
To run the Habit Tracker API locally, follow these steps:

### 1. Ensure Prerequisites are Installed
- Java Development Kit (JDK): Install JDK 17 or later. You can download it from AdoptOpenJDK.
- Maven: Ensure Maven is installed and available in your system's PATH. You can download it from Apache Maven.
- Ensure the MariaDB database is running. (Refer to the Running the Database in Docker section for instructions.)

### 2. Build the Project
First use Maven to build the project:

```text
mvn clean install
```

### 3. Run the Application 
Start the application using the following command:

```text
mvn spring-boot:run -DskipTests
```

### 4. Access the API
Once the application starts, it will be available at http://localhost:8080. You can interact with the API through tools like Postman, curl, or directly through a frontend application.

### 5. Verify the Application is Running
Visit http://localhost:8080/actuator/health in your browser or API client to check the application's health status. 
A response like {"status":"UP"} indicates the application is running successfully.

## Available Endpoints

This Habit Tracker API provides several endpoints to manage habits and track progress. Below is a list of all available endpoints, their HTTP methods, and descriptions:

### 1. **Get All Habits**
- **URL**: `/api/habits`
- **Method**: `GET`
- **Description**: Retrieves a list of all habits.
- **Response**: A JSON array of habit objects.

### 2. **Get Habit by ID**
- **URL**: `/api/habits/{id}`
- **Method**: `GET`
- **Description**: Retrieves a specific habit by its ID.
- **Parameters**:
    - `id`: The unique identifier of the habit (UUID).
- **Response**: A JSON object representing the habit with the specified ID.

### 3. **Create a New Habit**
- **URL**: `/api/habits`
- **Method**: `POST`
- **Description**: Creates a new habit.
- **Request Body**:
    ```json
    {
      "name": "New Habit",
      "description": "A new habit description",
      "frequency": "DAILY",
      "startDate": "2024-01-01"
    }
    ```
- **Response**: The created habit object with a generated `id`.

### 4. **Update an Existing Habit**
- **URL**: `/api/habits/{id}`
- **Method**: `PATCH`
- **Description**: Updates an existing habit by its ID.
- **Parameters**:
    - `id`: The unique identifier of the habit (UUID).
- **Request Body**:
    ```json
    {
      "name": "Updated Habit",
      "description": "Updated description",
      "frequency": "WEEKLY",
      "startDate": "2024-01-01"
    }
    ```
- **Response**: The updated habit object.

### 5. **Delete a Habit**
- **URL**: `/api/habits/{id}`
- **Method**: `DELETE`
- **Description**: Deletes a habit by its ID.
- **Parameters**:
    - `id`: The unique identifier of the habit (UUID).
- **Response**: No content (204 status) if successful.

### 6. **Add Tracking Entry**
- **URL**: `/api/habits/{habitId}/tracking`
- **Method**: `POST`
- **Description**: Adds a tracking entry for a specific habit.
- **Parameters**:
    - `habitId`: The ID of the habit to track (UUID).
- **Request Body**:
    ```json
    "First tracking entry"
    ```
- **Response**: A JSON object representing the created tracking entry with a timestamp and note.

### 7. **Get Trackings for a Habit**
- **URL**: `/api/habits/{habitId}/tracking`
- **Method**: `GET`
- **Description**: Retrieves all tracking entries for a specific habit.
- **Parameters**:
    - `habitId`: The unique identifier of the habit (UUID).
- **Response**: A JSON array of tracking objects.




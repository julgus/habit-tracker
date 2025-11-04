# Habit Tracker API
This project is a starter template for Netlight's Tech Avenue code assignment.
The task is to implement a Habit Tracker API that allows users to track their habits.
Here you'll find the [Original Assignment Description](https://netlight365beta-my.sharepoint.com/:w:/g/personal/date_netlight_com/EZMW_aVHkzxBlVNb5VYWiPoBnv0MWJ_LeBBlG8IV6QrOZw?e=cnbNlH)

## Project tech stack
This application leverages the following technologies to deliver a robust and scalable Habit Tracking API:

- **Java 17:** The programming language used
- **Spring Boot:** Handles web requests and makes it easy to create REST APIs.
- **Liquibase:** Keeps track of the database structure and handles updates automatically.
- **MariaDB:** Stores habits and tracking data.
- **JUnit 5 & Mockito:** Lets us test the code to make sure everything works.
- **Docker:** Runs MariaDB in a container, so we don’t have to install it manually.

The proposed tech stack reflects technologies commonly used by Netlight’s clients who build backend services in Java. However, it should not be seen as the only or recommended way to build a REST API.

Different companies choose their technology stacks based on many factors, such as:

- The existing skills and experience of their engineering teams 
- The specific use cases or business requirements 
- The cost and licensing of tools and platforms 
- Performance and reliability needs 
- Scalability and long-term maintenance considerations
- etc.

In other words, there’s no “one-size-fits-all” solution — the best technology stack depends on the specific context, goals, and constraints of each project.

You are therefore encouraged to use any stack you feel most comfortable with, or one you’d like to explore and learn.

If you choose to work with a different stack, you can still use this repository as a source of inspiration or reference for your implementation.
## Project structure
Here’s an overview of the files and folders that make up this application:

```plaintext
habit-tracker/
├── src/main/java/com/example/habittracker/
│   ├── controller/            # REST Controllers for API Endpoints
│   ├── model/                 # Database entities (Habit, Tracking)
│   ├── exception/             # Custom exceptions
│   ├── mapper/                # Mappings for habit responses
│   ├── dto/                   # Data Transfer objects
│        ├── request/          # Request objects
│        ├── response/         # Response objects
│   ├── repository/            # JPA Repositories for interacting with the database
│   ├── service/               # Service layer with business logic
│   └── HabitTrackerApplication.java  # Main Spring Boot application
├── src/main/resources/
│   ├── application.yml        # Configuration for database, server, etc.
│   ├── db/changelog/          # Liquibase migrations, defines database schema
│   └── db.changelog-master.xml # Liquibase master changelog
├── docker-compose.yml         # Docker configuration for MariaDB
└── README.md                  # Project documentation
```

## Getting started
Your goal is to implement the REST API by completing the `HabitTrackerController` and its underlying service, `HabitTrackerService`.

__To help you get started, the endpoints for creating a new habit and fetching all habits are already implemented.__

### Suggested workflow

1. **Explore the Project Files**  
   - Familiarize yourself with the key files:
     - `HabitTrackerController`
     - `HabitTrackerService`
   - Keep an eye out for the keyword "TODO" in all files, as it will hightlight sections that are not yet completed
2. **Run the Application**  
   Follow the instructions under **Run the Application** to start the server.

3. **Access the API Documentation**  
   Once the application is running, visit: http://localhost:8080/swagger-ui/index.html. This will display all available endpoints and allow you to interact with the API directly.

4. **Test Existing Endpoints**  
    Try out the endpoints that are already implemented for you:
- **Add a habit:** Use [`POST /api/habits`](http://localhost:8080/swagger-ui/index.html#/habit-tracker-controller/addHabit) to create a new habit in the database.
- **Fetch all habits:** Use [`GET /api/habits`](http://localhost:8080/swagger-ui/index.html#/habit-tracker-controller/getAllHabits) to retrieve all habits stored in the database.

5. **Implement Missing Endpoints**
Complete the TODOs in `HabitTrackerController` and the other files to implement the remaining functionality of the API.

6. **Run Tests Continuously**  
Use the provided test files (located in `src/test`) to verify your implementation. Running tests frequently will help ensure that the application behaves as expected as you add new features.
The tests are automatically run when you build the project using: 

   ```text
   mvn test
   ```
   
## Running the application 
To run the application, you must ensure the following prerequisites are met on your local computer:

- Java Development Kit (JDK): Install JDK 17 or later. You can download it from AdoptOpenJDK.
- Maven: Ensure Maven is installed and available in your system's PATH. You can download it from [Apache Maven](https://maven.apache.org/download.cgi).
- Ensure the MariaDB database is running. (Refer to the Running the Database in Docker section for instructions)

### Running the database in Docker 
Running your application requires that you have started the database. We've prepared a MariaDB setup with Docker.
To start the database with Docker, you simply use the following command to start the MariaDB container using Docker Compose: 

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

If you get the error below, you need to start your Docker deamon, e.g. by running the desktop application. 
```text
Cannot connect to the Docker daemon at unix:///Users/jugu/.docker/run/docker.sock. Is the docker daemon running?
```

### Run the application 
Once the database is started you can go ahead and build and run the application using: 

```text
mvn clean install
```

And then run the application using: 

```text
mvn spring-boot:run -DskipTests
```

### Access the API
Once the application starts, it will be available at http://localhost:8080/. 
You can interact with the API through tools like curl, or directly through the graphical Swagger API: http://localhost:8080/swagger-ui/index.html. 

## What to implement
The Habit Tracker API is considered complete when the following endpoints are available as specified below.
Remember that you can interact with the API using `curl` or [Swagger](http://localhost:8080/swagger-ui/index.html).

### 1. Create a New Habit
Users should be allowed to create a new habit by sending a `POST` request to `/api/habits` with the habit details in the request body.

**Request:**
```bash
curl -X POST http://localhost:8080/api/habits \
-H "Content-Type: application/json" \
-d '{
  "name": "Morning Exercise",
  "description": "A daily workout routine",
  "frequency": "DAILY",
  "startDate": "2024-01-01"
}'
```

**Response:**
```json
{
  "id": "a74fba36-951b-4e2d-b4db-10a6c3922f09",
  "name": "Morning Exercise",
  "description": "A daily workout routine",
  "frequency": "DAILY",
  "startDate": "2024-01-01"
}
```

### 2. Get All Habits
All habits should be retrievable by sending a GET request to /api/habits.

**Request:**
```bash
curl http://localhost:8080/api/habits
```
**Response:**
```json
[
  {
    "id": "a74fba36-951b-4e2d-b4db-10a6c3922f09",
    "name": "Morning Exercise",
    "description": "A daily workout routine",
    "frequency": "DAILY",
    "startDate": "2024-01-01"
  }
]
```

### 3.Get Habit by ID
You should allow users to fetch a specific habit by its ID by sending a GET request to /api/habits/{id}.

**Request:**
```bash
curl http://localhost:8080/api/habits/a74fba36-951b-4e2d-b4db-10a6c3922f09
```
**Response:**
```json
{
  "id": "a74fba36-951b-4e2d-b4db-10a6c3922f09",
  "name": "Morning Exercise",
  "description": "A daily workout routine",
  "frequency": "DAILY",
  "startDate": "2024-01-01"
}
```

### 4. Update a Habit
You should be able to update a habit by sending a PATCH request to /api/habits/{id} with the updated habit details in the request body.

**Request:**
```bash
curl -X PATCH http://localhost:8080/api/habits/a74fba36-951b-4e2d-b4db-10a6c3922f09 \
-H "Content-Type: application/json" \
-d '{
  "name": "Morning Exercise",
  "description": "A daily cardio and strength workout",
  "frequency": "DAILY",
  "startDate": "2024-01-01"
}'
```
**Response:**
```json
{
  "id": "a74fba36-951b-4e2d-b4db-10a6c3922f09",
  "name": "Morning Exercise",
  "description": "A daily cardio and strength workout",
  "frequency": "DAILY",
  "startDate": "2024-01-01"
}
```

### 5. Delete a Habit
User should be able to delete a habit by sending a DELETE request to /api/habits/{id}.

**Request:**
```bash
curl -X DELETE http://localhost:8080/api/habits/a74fba36-951b-4e2d-b4db-10a6c3922f09
```
Response: Status code: 204 No Content (indicating that the habit was successfully deleted).

### 6. Add a Tracking Entry
It should be possible to add a tracking entry to a habit by sending a POST request to /api/habits/{habitId}/tracking with a note in the request body.

**Request:**
```bash
curl -X POST http://localhost:8080/api/habits/a74fba36-951b-4e2d-b4db-10a6c3922f09/tracking \
-H "Content-Type: application/json" \
-d '"Completed 30 minutes of cardio workout"'
```
**Response:**
```json
{
  "id": "b9d90a5c-462d-4c07-ae1f-85b705b5f1e7",
  "habit": {
    "id": "a74fba36-951b-4e2d-b4db-10a6c3922f09",
    "name": "Morning Exercise",
    "description": "A daily cardio and strength workout",
    "frequency": "DAILY",
    "startDate": "2024-01-01"
  },
  "timestamp": "2024-01-01T08:30:00",
  "note": "Completed 30 minutes of cardio workout"
}
```

### 7. Get Tracking Entries for Habit
Users should be able to retrieve tracking entries for a specific habit by sending a GET request to /api/habits/{habitId}/tracking.

**Request:**
```bash
curl http://localhost:8080/api/habits/a74fba36-951b-4e2d-b4db-10a6c3922f09/tracking
```
**Response:**
```json
[
  {
    "id": "b9d90a5c-462d-4c07-ae1f-85b705b5f1e7",
    "habit": {
      "id": "a74fba36-951b-4e2d-b4db-10a6c3922f09",
      "name": "Morning Exercise",
      "description": "A daily cardio and strength workout",
      "frequency": "DAILY",
      "startDate": "2024-01-01"
    },
    "timestamp": "2024-01-01T08:30:00",
    "note": "Completed 30 minutes of cardio workout"
  }
]
```
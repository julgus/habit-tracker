# Habit Tracker API
This project is a Habit Tracking API built with **Spring Boot**, 
**Liquibase** for database migrations, and a **MariaDB** test container for storage. 
This REST API allows users to track their habits and log their progress, 
enabling them to establish goals, observe behavioral patterns, and enhance productivity.

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

## Features

- **CRUD Operations** for managing habits
- Persists habits in a **MariaDB** database that runs in a Docker container
- **Liquibase** for database version control

## Project Structure

```plaintext
habit-tracker/
├── src/main/java/com/example/habittracker/
│   ├── controller/            # REST Controllers for API Endpoints
│   ├── model/                 # Entities (Habit, Tracking)
│   ├── dto/                 # Entities (Habit, Tracking)
│        ├── request/          # Request objects
│        ├── response/         # Response objects
│   ├── repository/            # JPA Repositories
│   ├── service/               # Service layer with business logic
│   └── HabitTrackerApplication.java  # Main Spring Boot application
├── src/main/resources/
│   ├── application.yml        # Configuration for database, server, etc.
│   ├── db/changelog/          # Liquibase migrations
│   └── db.changelog-master.xml # Liquibase master changelog
├── docker-compose.yml         # Docker configuration for MariaDB
└── README.md                  # Project documentation

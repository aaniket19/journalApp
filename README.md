# Journal Entry Management Service

A secure backend application built using **Java 21**, **Spring Boot 3.5**, **Spring Security**, and **MongoDB** that allows users to manage personal journal entries through RESTful APIs. The application provides authentication, authorization, and role-based access control to ensure secure access to user data.

## Features

* User registration and authentication
* Secure password storage using BCrypt hashing
* Role-Based Access Control (RBAC)
* Create, Read, Update, and Delete (CRUD) journal entries
* User-specific journal management
* RESTful API architecture
* MongoDB Atlas integration
* Spring Security-based endpoint protection
* Layered architecture (Controller-Service-Repository)

## Technology Stack

| Category            | Technology                |
| ------------------- | ------------------------- |
| Language            | Java 21                   |
| Framework           | Spring Boot 3.5           |
| Security            | Spring Security 6         |
| Database            | MongoDB Atlas             |
| Build Tool          | Maven                     |
| API Style           | REST                      |
| Authentication      | HTTP Basic Authentication |
| Password Encryption | BCrypt                    |

## Architecture

The project follows a layered architecture:

```text
Client
   |
REST Controllers
   |
Service Layer
   |
Repository Layer
   |
MongoDB Atlas
```

### Components

* **Controller Layer**

  * Handles incoming HTTP requests.
  * Exposes REST endpoints.

* **Service Layer**

  * Contains business logic.
  * Manages user and journal operations.

* **Repository Layer**

  * Uses Spring Data MongoDB.
  * Performs database interactions.

## API Modules

### User Management

* Register User
* Retrieve User Details
* Update User Information
* Delete User

### Journal Management

* Create Journal Entry
* View Journal Entries
* Update Journal Entry
* Delete Journal Entry

### Admin Operations

* Role-based endpoint access
* Administrative operations secured through Spring Security

## Security Features

* Spring Security Integration
* BCrypt Password Encoding
* Role-Based Authorization
* Protected API Endpoints
* User-Specific Data Access

## Database Design

### User

```json
{
  "id": "ObjectId",
  "username": "string",
  "password": "encrypted",
  "roles": ["USER"],
  "journalEntries": []
}
```

### Journal Entry

```json
{
  "id": "ObjectId",
  "title": "string",
  "content": "string",
  "date": "timestamp"
}
```

## Getting Started

### Prerequisites

* Java 21
* Maven 3.9+
* MongoDB Atlas Account

### Clone Repository

```bash
git clone https://github.com/aaniket19/journalApp.git
cd journalApp
```

### Configure MongoDB

Update `application.properties`:

```properties
spring.data.mongodb.uri=<your-mongodb-uri>
spring.data.mongodb.database=journalDb
```

### Run Application

```bash
mvn clean install
mvn spring-boot:run
```

Application will start on:

```text
http://localhost:8080
```

## Future Enhancements

* JWT Authentication
* Refresh Token Support
* Docker Containerization
* Kubernetes Deployment
* API Documentation with Swagger/OpenAPI
* CI/CD using GitHub Actions
* Audit Logging and Monitoring

## Learning Outcomes

This project demonstrates practical experience with:

* Spring Boot Application Development
* REST API Design
* Spring Security Implementation
* MongoDB Data Modeling
* Layered Architecture
* Authentication and Authorization
* Backend Engineering Best Practices

## Author

**Aniket Magade**

GitHub: https://github.com/aaniket19

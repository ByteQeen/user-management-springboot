# FiBank: Banking System with User Management

A Spring Boot backend project for a banking system with secure user management and role-based access.

## Features Implemented
- User signup, login, and logout
- JWT authentication and refresh token mechanism
- Role-based security (USER, ADMIN)
- Redis integration for token management

## Features Planned / Work in Progress
- Money transfer (via card, phone, or MIA with no commission)
- OAuth2 authentication
- Forgot password functionality

## Technologies
- Java, Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- Redis
- PostgreSQL

## Setup Instructions
1. **Clone the repository:**
```bash
git clone <repository-url>

## Run the application:
./mvnw spring-boot:run


The application will start on:
http://localhost:8080 (HTTP)
https://localhost:8443 (HTTPS, if keystore configured)
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
1.Clone the repository:
   git clone <repository-url>
   cd <repository-folder>

2.Set environment variables / VM options
Before running the application, set the required variables either as VM options in IntelliJ or in your system environment:

Example VM options for dev profile
-Dspring.profiles.active=dev \
-DDBA_URL=jdbc:postgresql://<your_db_host>:<port>/<your_db_name>
-DDBA_USERNAME=your_db_user \
-DDBA_PASSWORD=your_db_password \
-DJWTA_SECRET=your_jwt_secret_here \

Example VM options for prod profile
-Dspring.profiles.active=prod \
-DDBU_URL=jdbc:postgresql://<your_db_host>:<port>/<your_db_name>
-DDBU_USERNAME=your_db_user \
-DDBU_PASSWORD=your_db_password \
-DJWTU_SECRET=your_jwt_secret_here \
-DSSL_KEYSTORE_PASSWORD=your_keystore_password
Note: Replace the values with your actual secrets. These variables are used in application-dev.properties and application-prod.properties.

3.Run the application
From IntelliJ, run the main class FibankApplication with the appropriate profile.
Or from terminal:
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev


4.Access the application
Dev (HTTP): http://localhost:8080
Prod (HTTPS): https://localhost:8443 (requires keystore configured)
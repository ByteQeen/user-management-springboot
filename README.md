# 💳 FiBank: Banking System with User Management

A **Spring Boot** backend project for a banking system with secure user management and role-based access control.

---

## 🚀 Features Implemented

- 🧾 User signup, login, and logout
- 🔐 JWT authentication and refresh token mechanism
- 👥 Role-based security (**USER**, **ADMIN**)
- ⚡ Redis integration for token management

---

## 🧩 Features Planned / Work in Progress

- 💸 Money transfer (via card, phone, or MIA — no commission)
- 🔑 OAuth2 authentication
- 🔁 Forgot password functionality

---

## 🧰 Technologies Used

- **Java**, **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **Redis**
- **PostgreSQL**

---

## 🛠️ Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd <repository-folder>
```

### 2. Configure Environment Variables

Before running the application, you must tell Spring Boot **which environment (profile)** to use and provide the required **database and security credentials**.

You can do this in two ways:

1. By adding **VM options** in your IDE (for example, IntelliJ IDEA)
2. Or by setting **environment variables** in your operating system

---

#### ⚙️ Example: Development Profile Configuration

When running in **development mode**, add these VM options or environment variables:

```bash
-DDBA_URL=jdbc:postgresql://<your_db_host>:/<your_db_name>
-DDBA_USERNAME=your_db_user
-DDBA_PASSWORD=your_db_password
-DJWTA_SECRET=your_jwt_secret_here
```

#### ⚙️ Example: Production Profile Configuration

When running in **production mode**, add these VM options or environment variables:

```bash
-DDBU_URL=jdbc:postgresql://<your_db_host>:/<your_db_name>
-DDBU_USERNAME=your_db_user
-DDBU_PASSWORD=your_db_password
-DJWTU_SECRET=your_jwt_secret_here
-DSSL_KEYSTORE_PASSWORD=your_keystore_password
```

> **Note:** Replace the values with your actual secrets. These variables are used in `application-dev.properties` and `application-prod.properties`.

### 3. Run the Application

#### ▶️ From IntelliJ IDEA

Run the main class and make sure you select the appropriate profile (`dev` or `prod`).

#### 💻 From Terminal

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### 4. Access the Application

- **Development (HTTP):** [http://localhost:8080](http://localhost:8080)
- **Production (HTTPS):** [https://localhost:8443](https://localhost:8443) _(requires keystore configured)_

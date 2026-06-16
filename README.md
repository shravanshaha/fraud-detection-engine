# Fraud Detection Engine

A production-ready fraud detection backend built using Java Spring Boot that analyzes financial transactions using a rule-based risk scoring engine.

The system evaluates transactions against multiple fraud detection rules, calculates a risk score, determines whether a transaction should be allowed, reviewed, or blocked, and stores a complete fraud audit trail for analysis.

---

## 🚀 Live Demo

### API Base URL

https://fraud-detection-engine-htxl.onrender.com

### Swagger Documentation

https://fraud-detection-engine-htxl.onrender.com/swagger-ui/index.html

## ⚠️ First request may take 1–2 minutes because the application is hosted on Render free tier.

## 🏗️ System Architecture

```
Client
  |
  v
REST API (Spring Boot Controllers)
  |
  v
Transaction Service
  |
  v
Fraud Rule Engine
  |
  +--> Amount Deviation Rule
  |
  +--> Merchant Blacklist Rule
  |
  +--> Velocity Rule
  |
  v
Risk Score Calculation
  |
  v
Transaction Decision
(ALLOWED / REVIEW / BLOCKED)
  |
  v
JPA / Hibernate
  |
  v
PostgreSQL Database (Supabase)
```

---

## ✨ Features

- Rule-based fraud detection engine
- Fraud risk score calculation
- Transaction classification:
  - ALLOWED
  - REVIEW
  - BLOCKED
- Fraud audit trail with rule evaluation history
- Account management APIs
- REST APIs documented with OpenAPI/Swagger
- Request validation using Jakarta Validation
- Global exception handling
- Structured logging using SLF4J
- Environment-specific configuration using Spring Profiles
- Automated unit testing using JUnit 5 and Mockito
- Docker-based production deployment

---

## 🛠️ Tech Stack

### Backend

- Java 21
- Spring Boot 3
- Spring Data JPA
- Hibernate ORM
- Maven

### Database

- MySQL 8 (Local Development)
- PostgreSQL (Production)
- Supabase Cloud Database

### Testing

- JUnit 5
- Mockito

### DevOps & Deployment

- Docker
- Render
- Environment Variables
- Spring Profiles

---

## 🧠 Fraud Detection Rules

### 1. Amount Deviation Rule

Detects unusually high transactions compared to an account's average transaction behavior.

### 2. Merchant Blacklist Rule

Detects transactions involving known fraudulent merchants.

### 3. Velocity Rule

Detects suspicious transaction frequency within a short period.

---

## 📌 API Endpoints

### Account APIs

| Method | Endpoint             | Description              |
| ------ | -------------------- | ------------------------ |
| POST   | `/api/accounts`      | Create a new account     |
| GET    | `/api/accounts/{id}` | Retrieve account details |

---

### Transaction APIs

| Method | Endpoint            | Description                                    |
| ------ | ------------------- | ---------------------------------------------- |
| POST   | `/api/transactions` | Process transaction and perform fraud analysis |

---

## ⚙️ Local Setup

### Prerequisites

- Java 21
- Maven
- Docker Desktop

---

### Clone Repository

```bash
git clone https://github.com/shravanshaha/fraud-detection-engine.git

cd fraud-detection-engine
```

---

### Start MySQL Using Docker

```bash
docker run --name fraud_mysql \
-p 3307:3306 \
-e MYSQL_ROOT_PASSWORD=<root_password> \
-e MYSQL_DATABASE=fraud_db \
-e MYSQL_USER=<database_username> \
-e MYSQL_PASSWORD=<database_password> \
-d mysql:8.0
```

Update `application-dev.properties` with the same database credentials used while starting the MySQL container.

---

### Run the Application

```bash
mvn spring-boot:run
```

Application URL:

```
http://localhost:8080
```

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🌐 Production Deployment

The production environment uses:

- Render for application hosting
- Docker container deployment
- PostgreSQL database hosted on Supabase
- Environment-based configuration using Spring Profiles

---

## 📋 Logging & Monitoring

Application logs capture:

- Transaction processing
- Fraud rule execution
- Risk score calculation
- Database persistence operations
- Application startup events

Production logs can be monitored using the Render dashboard.

---

## 🚀 Future Improvements

- Machine learning-based anomaly detection
- Kafka-based real-time transaction streaming
- Redis caching for faster fraud checks
- JWT authentication and role-based authorization
- Admin dashboard for fraud monitoring
- Email/SMS fraud alert notifications

---

## 👨‍💻 Author

**Shravan Shaha**

GitHub:
https://github.com/shravanshaha

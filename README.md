# 🛒 store.io – E-commerce Microservices Platform

## 📌 Project Title

**store.io – Scalable E-commerce System using Microservices Architecture**

---

## 📖 System Overview

**store.io** is a full-stack e-commerce platform designed using a **microservices architecture**.
The system simulates a real-world online shopping experience where users can browse products, manage carts, place orders, and complete payments.

The goal of this project is to **learn and implement real-world full-stack development practices**, including:

* Microservices design
* Service-to-service communication
* Backend for Frontend (BFF) pattern
* Scalable and maintainable architecture

This project is built incrementally, starting from core services and expanding into advanced features.

---

## 🛠️ Tech Stack

### Backend (Microservices)

* Java + Spring Boot
* Spring Web (REST APIs)
* Spring Security (JWT - planned)
* Spring Data JPA

### Database

* MySQL (separate DB per service)

### BFF Layer

* Node.js (Express.js)

### Frontend

* React.js (planned)

### Tools

* Postman (API testing)
* Git & GitHub (version control)
* Docker (planned)

---

## 🧩 Microservices List

| Service           | Description                                          |
| ----------------- | ---------------------------------------------------- |
| User/Auth Service | Handles user registration, login, and authentication |
| Product Service   | Manages product catalog and details                  |
| Inventory Service | Tracks product stock availability                    |
| Cart Service      | Manages user shopping cart                           |
| Order Service     | Handles order creation and management                |
| Payment Service   | Processes (mock) payments                            |

> 🔄 Additional services like Discount, Review, and Notification will be added later.

---

## 🚀 How to Run Each Service

> ⚠️ Each service runs independently on different ports.

### General Steps (for each service)

```bash
cd backend/<service-name>
./mvnw spring-boot:run
```

### Example

```bash
cd backend/user-service
./mvnw spring-boot:run
```

### Default Ports (can be changed later)

| Service           | Port |
| ----------------- | ---- |
| User Service      | 8081 |
| Product Service   | 8082 |
| Inventory Service | 8083 |
| Cart Service      | 8084 |
| Order Service     | 8085 |
| Payment Service   | 8086 |

---

## 📡 API Documentation

API documentation is maintained using:

* Postman Collection (available in `/postman` folder)

> Future improvements:

* Swagger/OpenAPI documentation per service

---

## 🗄️ Database Setup

Each microservice has its own database.

### Example Databases

```text
user_db
product_db
inventory_db
cart_db
order_db
payment_db
```

### Setup Steps

1. Install MySQL
2. Create databases manually:

```sql
CREATE DATABASE user_db;
CREATE DATABASE product_db;
CREATE DATABASE inventory_db;
CREATE DATABASE cart_db;
CREATE DATABASE order_db;
CREATE DATABASE payment_db;
```

3. Update `application.properties` in each service:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/<db_name>
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## 🧪 Postman Testing Guide

1. Open Postman
2. Import collection from:

```text
/postman/ecommerce-api-collection.json
```

3. Set environment variables:

```text
base_user_url = http://localhost:8081
base_product_url = http://localhost:8082
base_cart_url = http://localhost:8084
...
```

4. Test APIs in this order:

```text
1. Register user
2. Login → get JWT token
3. Create products
4. Add to cart
5. Place order
6. Make payment
```

---

## 📈 Current Progress

### ✅ Completed

* [ ] Project structure setup
* [ ] GitHub repository initialized

### 🔧 In Progress

* [ ] User/Auth Service (Register & Login)
* [ ] Product Service (Basic CRUD)

### ⏳ Planned

* [ ] Inventory Service
* [ ] Cart Service
* [ ] Order Service
* [ ] Payment Service
* [ ] Node.js BFF Layer
* [ ] React Frontend
* [ ] Docker setup
* [ ] API Gateway & Service Discovery

---

## 📌 Future Enhancements

* Discount/Coupon Service
* Review & Rating System
* Notification Service (Email/SMS)
* Admin Dashboard
* Microservices communication using Kafka/RabbitMQ
* CI/CD Pipeline
* Cloud Deployment (AWS)

---

## 👨‍💻 Learning Goals

This project focuses on:

* Thinking like a real full-stack developer
* Designing scalable backend systems
* Understanding service boundaries
* Building production-like architecture

---

## 📎 Notes

This project is built step-by-step for learning purposes.
Each phase improves the system design, performance, and scalability.

---

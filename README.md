# store.io

A scalable e-commerce platform built using a microservices architecture.

The project is designed as a learning-focused full-stack system that follows modern software engineering practices including service decomposition, Backend for Frontend (BFF), RESTful APIs, event-driven communication, containerization, and distributed system design.

---

## Architecture Overview

```text
React Frontend
       │
       ▼
Node.js BFF
       │
 ┌─────┼─────────────────┐
 ▼     ▼      ▼          ▼

User  Product Cart    Order
Svc    Svc    Svc      Svc

              │
              ▼

         Payment Svc
```

Each service owns its own database and communicates through well-defined APIs.

---

## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Security
* MySQL
* OpenFeign

### Frontend

* React
* Vite
* Axios

### BFF

* Node.js
* Express.js

### Infrastructure

* Docker
* Docker Compose
* Kafka / RabbitMQ (planned)
* Redis (planned)

### Development Tools

* Git
* GitHub
* Postman

---

## Services

### User Service

Responsible for:

* User registration
* Authentication
* User profiles
* JWT token generation

### Product Service

Responsible for:

* Product catalog
* Categories
* Inventory management

### Cart Service

Responsible for:

* Shopping cart management
* Quantity updates
* Cart retrieval

### Order Service

Responsible for:

* Order creation
* Order history
* Order status management

### Payment Service

Responsible for:

* Payment processing
* Transaction tracking
* Payment status updates

### BFF Service

Responsible for:

* API aggregation
* Frontend-specific responses
* Authentication middleware
* Request orchestration

---

## Project Structure

```text
store.io/
├── backend/
├── frontend/
├── bff/
├── infrastructure/
├── docs/
└── scripts/
```

---

## Getting Started

### Clone Repository

```bash
git clone https://github.com/your-username/store.io.git
cd store.io
```

### Start Infrastructure

```bash
docker-compose up -d
```

### Run Services

Start each Spring Boot service:

```bash
mvn spring-boot:run
```

Run BFF:

```bash
npm install
npm run dev
```

Run Frontend:

```bash
npm install
npm run dev
```

---

## Learning Objectives

This project is focused on learning:

* Microservices Architecture
* Service Communication
* API Design
* Authentication & Authorization
* Event-Driven Systems
* Docker & Containerization
* Distributed System Concepts
* Full-Stack Development

---

## Roadmap

### Phase 1

* Product Service
* User Service
* Cart Service
* Order Service

### Phase 2

* JWT Authentication
* Role-Based Access Control

### Phase 3

* Payment Service

### Phase 4

* Service-to-Service Communication
* Kafka/RabbitMQ Integration

### Phase 5

* Docker Compose
* Redis Caching

### Phase 6

* Monitoring & Observability
* Prometheus
* Grafana

---

## License

This project is developed for educational and portfolio purposes.

# store.io — Enterprise E-Commerce Microservices Platform

[![Architecture](https://img.shields.io/badge/Architecture-Microservices-blue)](https://github.com/ramlanrinos/store.io)
[![Backend](https://img.shields.io/badge/Backend-Spring%20Boot%203.x-green)](https://spring.io/projects/spring-boot)
[![BFF](https://img.shields.io/badge/BFF-Node.js%20%2F%20Express-lightgrey)](https://nodejs.org/)
[![Frontend](https://img.shields.io/badge/Frontend-React-61dafb)](https://react.dev/)

**store.io** is a production-grade full-stack e-commerce platform built using modern microservices architecture, Backend-for-Frontend (BFF) pattern, and React frontend.

---

## 🏛️ High-Level Architecture

```text
React Frontend
      │
      ▼
 Node.js BFF
      │
      ├──► User / Auth Service      (Spring Boot + MySQL) [Port 8081]
      ├──► Product Catalog Service  (Spring Boot + MySQL) [Port 8082]
      ├──► Inventory Service        (Spring Boot + MySQL) [Port 8083]
      ├──► Cart Service             (Spring Boot + MySQL) [Port 8084]
      ├──► Order Service            (Spring Boot + MySQL) [Port 8085]
      └──► Payment Service          (Spring Boot + MySQL) [Port 8086]
```

---

## 🛠️ Technology Stack

* **Backend Microservices:** Java 21, Spring Boot 3.2.3, Spring Data JPA, Spring Security, JWT (JJWT 0.12.5), Maven
* **Database:** MySQL (Database-per-Service isolation)
* **BFF (Backend-For-Frontend):** Node.js, Express.js
* **Frontend:** React.js, HTML5, CSS3, ES6+ JavaScript
* **Documentation & Testing:** Postman Collections, RFC 7807 Error Specs, Markdown Docs

---

## 📂 Repository Structure

```text
store.io/
├── backend/
│   ├── user-service/        (Port 8081)
│   ├── product-service/     (Port 8082)
│   ├── inventory-service/   (Port 8083)
│   ├── cart-service/        (Port 8084)
│   ├── order-service/       (Port 8085)
│   └── payment-service/     (Port 8086)
├── bff/
│   └── node-bff/
├── frontend/
│   └── react-app/
├── docs/
│   ├── requirements.md
│   ├── architecture.md
│   ├── database-design.md
│   ├── api-design.md
│   └── progress-log.md
├── postman/
└── README.md
```

---

## 🗺️ Project Roadmap & Development Phases

- [x] **Phase 1: Requirements Analysis**
- [x] **Phase 2: Architecture Design**
- [x] **Phase 3: Database Design**
- [x] **Phase 4: Backend Development**
- [ ] **Phase 5: Postman Testing** (Current)
- [ ] **Phase 6: Node.js BFF**
- [ ] **Phase 7: React Frontend**
- [ ] **Phase 8: Dockerization**
- [ ] **Phase 9: Advanced Microservices Features**

---

## 📄 Documentation

Detailed project specifications are maintained in the [`docs/`](file:///run/media/rinos/Data/store.io/docs) directory:
* [`docs/requirements.md`](file:///run/media/rinos/Data/store.io/docs/requirements.md): System Functional & Non-Functional Requirements.
* [`docs/architecture.md`](file:///run/media/rinos/Data/store.io/docs/architecture.md): System Architecture, JWT propagation, and Checkout Saga sequence.
* [`docs/database-design.md`](file:///run/media/rinos/Data/store.io/docs/database-design.md): Database-per-Service ER models & SQL indexing rules.
* [`docs/progress-log.md`](file:///run/media/rinos/Data/store.io/docs/progress-log.md): Implementation step-by-step progress tracking.

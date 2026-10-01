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
      ├──► User / Auth Service      (Spring Boot + MySQL)
      ├──► Product Catalog Service  (Spring Boot + MySQL)
      ├──► Inventory Service        (Spring Boot + MySQL)
      ├──► Cart Service             (Spring Boot + MySQL)
      ├──► Order Service            (Spring Boot + MySQL)
      └──► Payment Service          (Spring Boot + MySQL)
```

---

## 🛠️ Technology Stack

* **Backend Microservices:** Java 17+, Spring Boot 3.x, Spring Data JPA, Spring Security, JWT, Maven
* **Database:** MySQL (per-service database isolation)
* **BFF (Backend-For-Frontend):** Node.js, Express.js
* **Frontend:** React.js, HTML5, CSS3, ES6+ JavaScript
* **Documentation & Testing:** Postman, Markdown Docs

---

## 📂 Repository Structure

```text
store.io/
├── backend/
│   ├── user-service/
│   ├── product-service/
│   ├── inventory-service/
│   ├── cart-service/
│   ├── order-service/
│   └── payment-service/
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
- [ ] **Phase 3: Database Design** (Current)
- [ ] **Phase 4: Backend Development**
- [ ] **Phase 5: Postman Testing**
- [ ] **Phase 6: Node.js BFF**
- [ ] **Phase 7: React Frontend**
- [ ] **Phase 8: Dockerization**
- [ ] **Phase 9: Advanced Microservices Features**

---

## 📄 Documentation

Detailed project specifications are maintained in the [`docs/`](file:///run/media/rinos/Data/store.io/docs) directory:
* [`docs/requirements.md`](file:///run/media/rinos/Data/store.io/docs/requirements.md): System Functional & Non-Functional Requirements.
* [`docs/architecture.md`](file:///run/media/rinos/Data/store.io/docs/architecture.md): System Architecture, JWT propagation, and Checkout Saga sequence.
* [`docs/progress-log.md`](file:///run/media/rinos/Data/store.io/docs/progress-log.md): Implementation step-by-step progress tracking.

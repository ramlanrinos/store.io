# store.io — Progress Log

This log tracks completed phases, decisions made, and upcoming milestones for the `store.io` platform.

---

## 📅 Log Entries

### [2026-10-01] — Phase 4: Backend Development (user-service) Completed
- **Status:** Completed
- **Deliverables:**
  - Implemented initial Spring Boot microservice at [`backend/user-service`](file:///run/media/rinos/Data/store.io/backend/user-service).
  - Configured Maven build (`pom.xml`, Spring Boot 3.2.3, Java 21, Spring Security, Spring Data JPA, JJWT 0.12.5).
  - Created JPA entities (`User`, `Role`) and repositories (`UserRepository`, `RoleRepository`).
  - Implemented BCrypt password hashing (`PasswordEncoderConfig`).
  - Implemented JWT Token Provider (`JwtTokenProvider`) for HMAC SHA-256 signed stateless tokens.
  - Implemented REST API Endpoints:
    - `POST /users/register` — User registration with role assignment & email uniqueness check.
    - `POST /users/login` — Authentication returning signed JWT token & user claims.
    - `GET /users/profile` — User profile retrieval via token or `X-User-Id` header.
    - `GET /users/validate` — Token validation endpoint for BFF and microservice communication.
  - Added RFC 7807 problem details global exception handling (`GlobalExceptionHandler`).
  - Verified 100% build & unit test pass rate (`AuthServiceTest`, `UserServiceApplicationTests`).

### [2026-10-01] — Phase 3: Database Design Completed
- **Status:** Completed
- **Deliverables:**
  - Created Database Design specification in [`docs/database-design.md`](file:///run/media/rinos/Data/store.io/docs/database-design.md).
  - Designed ER diagrams & table schemas for all 6 service databases (`user_db`, `product_db`, `inventory_db`, `cart_db`, `order_db`, `payment_db`).
  - Incorporated optimistic locking columns (`version` in `inventory_db`) to handle concurrent flash-sale stock deductions cleanly.
  - Enforced financial snapshotting (`price_snapshot` in `order_items`) to lock prices at checkout.
  - Configured high-performance B-tree indexes (`email`, `sku`, `category_id`, `product_id`, `user_id`, `order_id`, `status`).

### [2026-10-01] — Phase 2: Architecture Design Completed
- **Status:** Completed
- **Deliverables:**
  - Created System Architecture document in [`docs/architecture.md`](file:///run/media/rinos/Data/store.io/docs/architecture.md).
  - Modeled System Topology (React $\rightarrow$ Node.js BFF $\rightarrow$ 6 Spring Boot Services $\rightarrow$ MySQL DB per service).
  - Modeled Security & Authentication Architecture (Stateless JWT token issuance, verification, header enrichment `X-User-Id`, `X-User-Roles`).
  - Designed Orchestrated Saga pattern for Checkout transaction flow (Cart $\rightarrow$ Order $\rightarrow$ Inventory Reserve $\rightarrow$ Payment $\rightarrow$ Stock Release on failure).
  - Established API error response standard (RFC 7807 Problem Details).

### [2026-10-01] — Phase 1: Requirements Analysis Completed
- **Status:** Completed
- **Deliverables:**
  - Root repository README initialized with architecture diagrams, stack overview, and roadmap.
  - Comprehensive System Requirements Specification created at [`docs/requirements.md`](file:///run/media/rinos/Data/store.io/docs/requirements.md).
  - Defined functional bounds for 6 initial microservices (`user-service`, `product-service`, `inventory-service`, `cart-service`, `order-service`, `payment-service`).
  - Defined Non-Functional Requirements (Security, Performance, Concurrency, Isolation).
  - Drafted Edge Cases & Risk Mitigation Matrix (Flash sale concurrency, Price snapshotting, Payment failures).

---

## 📌 Phase Tracker Summary

| Phase | Description | Status |
|---|---|---|
| **Phase 1** | Requirements Analysis | 🟢 Completed |
| **Phase 2** | Architecture Design | 🟢 Completed |
| **Phase 3** | Database Design | 🟢 Completed |
| **Phase 4** | Backend Development | 🟢 `user-service` Completed / Remaining Services Pending |
| **Phase 5** | Postman Testing | ⚪ Pending |
| **Phase 6** | Node.js BFF | ⚪ Pending |
| **Phase 7** | React Frontend | ⚪ Pending |
| **Phase 8** | Dockerization | ⚪ Pending |
| **Phase 9** | Advanced Microservices Features | ⚪ Pending |

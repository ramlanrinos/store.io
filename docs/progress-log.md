# store.io — Progress Log

This log tracks completed phases, decisions made, and upcoming milestones for the `store.io` platform.

---

## 📅 Log Entries

### [2026-10-01] — Phase 4 Step 3: Backend Development (inventory-service) Completed
- **Status:** Completed
- **Deliverables:**
  - Implemented Inventory Microservice at [`backend/inventory-service`](file:///run/media/rinos/Data/store.io/backend/inventory-service).
  - Configured Spring Boot 3.2.3 + Java 21 on port `8083`, connecting to `inventory_db`.
  - Implemented `@Version` optimistic concurrency lock on `inventories` entity to prevent flash-sale overselling.
  - Implemented REST APIs & Saga Transaction Endpoints:
    - `GET /inventory/{productId}` — Product stock lookup (`availableQuantity`, `reservedQuantity`).
    - `POST /inventory/reserve` — Reserves stock during checkout (`available` $\rightarrow$ `reserved`).
    - `POST /inventory/release` — Restores reserved stock on payment failure (`reserved` $\rightarrow$ `available`).
    - `POST /inventory/deduct` — Finalizes stock deduction upon payment approval.
    - `POST /inventory/restock` — Admin restock & audit log generation (`inventory_logs`).
  - Added RFC 7807 global exception handling (`GlobalExceptionHandler`).
  - Verified 100% build & unit test pass rate (`InventoryServiceTest`, `InventoryServiceApplicationTests`).

### [2026-10-01] — Phase 4 Step 2: Backend Development (product-service) Completed
- **Status:** Completed
- **Deliverables:**
  - Implemented Product Catalog Microservice at [`backend/product-service`](file:///run/media/rinos/Data/store.io/backend/product-service).
  - Implemented paginated catalog, category search, and multi-ID batch lookup (`POST /products/batch`).

### [2026-10-01] — Phase 4 Step 1: Backend Development (user-service) Completed
- **Status:** Completed
- **Deliverables:**
  - Implemented User & Auth Microservice at [`backend/user-service`](file:///run/media/rinos/Data/store.io/backend/user-service).
  - Implemented JWT authentication and profile endpoints (`POST /users/register`, `POST /users/login`, `GET /users/profile`).

### [2026-10-01] — Phase 3: Database Design Completed
- **Status:** Completed

### [2026-10-01] — Phase 2: Architecture Design Completed
- **Status:** Completed

### [2026-10-01] — Phase 1: Requirements Analysis Completed
- **Status:** Completed

---

## 📌 Phase Tracker Summary

| Phase | Description | Status |
|---|---|---|
| **Phase 1** | Requirements Analysis | 🟢 Completed |
| **Phase 2** | Architecture Design | 🟢 Completed |
| **Phase 3** | Database Design | 🟢 Completed |
| **Phase 4** | Backend Development | 🟢 `user`, `product`, `inventory` Completed / `cart`, `order`, `payment` Pending |
| **Phase 5** | Postman Testing | ⚪ Pending |
| **Phase 6** | Node.js BFF | ⚪ Pending |
| **Phase 7** | React Frontend | ⚪ Pending |
| **Phase 8** | Dockerization | ⚪ Pending |
| **Phase 9** | Advanced Microservices Features | ⚪ Pending |

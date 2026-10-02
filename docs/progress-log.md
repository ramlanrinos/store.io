# store.io — Progress Log

This log tracks completed phases, decisions made, and upcoming milestones for the `store.io` platform.

---

## 📅 Log Entries

### [2026-10-02] — Phase 4 Step 5: Backend Development (order-service) Completed
- **Status:** Completed
- **Deliverables:**
  - Implemented Order Microservice at [`backend/order-service`](file:///run/media/rinos/Data/store.io/backend/order-service).
  - Configured Spring Boot 3.2.3 + Java 21 on port `8085`, connecting to `order_db`.
  - Implemented financial price snapshotting (`price_snapshot` in `order_items`) to lock prices permanently at order placement time.
  - Implemented Order State Machine (`PENDING`, `PAID`, `PAYMENT_FAILED`, `SHIPPED`, `DELIVERED`, `CANCELLED`).
  - Implemented REST APIs:
    - `POST /orders` — Order creation with unique business order number generation (e.g., `ORD-20261002-A1B2C3`) and subtotal/total calculation.
    - `GET /orders/{id}` — Fetch order details by ID.
    - `GET /orders/number/{orderNumber}` — Fetch order details by business reference number.
    - `GET /orders/user/{userId}` — Retrieve customer order history.
    - `PUT /orders/{id}/status` — Update order state lifecycle.
  - Added RFC 7807 global exception handling (`GlobalExceptionHandler`).
  - Verified 100% build & unit test pass rate (`OrderServiceTest`, `OrderServiceApplicationTests`).

### [2026-10-02] — Phase 4 Step 4: Backend Development (cart-service) Completed
- **Status:** Completed

### [2026-10-01] — Phase 4 Step 3: Backend Development (inventory-service) Completed
- **Status:** Completed

### [2026-10-01] — Phase 4 Step 2: Backend Development (product-service) Completed
- **Status:** Completed

### [2026-10-01] — Phase 4 Step 1: Backend Development (user-service) Completed
- **Status:** Completed

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
| **Phase 4** | Backend Development | 🟢 `user`, `product`, `inventory`, `cart`, `order` Completed / `payment` Pending |
| **Phase 5** | Postman Testing | ⚪ Pending |
| **Phase 6** | Node.js BFF | ⚪ Pending |
| **Phase 7** | React Frontend | ⚪ Pending |
| **Phase 8** | Dockerization | ⚪ Pending |
| **Phase 9** | Advanced Microservices Features | ⚪ Pending |

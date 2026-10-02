# store.io — Progress Log

This log tracks completed phases, decisions made, and upcoming milestones for the `store.io` platform.

---

## 📅 Log Entries

### [2026-10-02] — Phase 4 Step 4: Backend Development (cart-service) Completed
- **Status:** Completed
- **Deliverables:**
  - Implemented Cart Microservice at [`backend/cart-service`](file:///run/media/rinos/Data/store.io/backend/cart-service).
  - Configured Spring Boot 3.2.3 + Java 21 on port `8084`, connecting to `cart_db`.
  - Created JPA entities (`Cart`, `CartItem`) with cascading orphan removal and `user_id` index.
  - Implemented REST APIs:
    - `GET /carts/{userId}` — Retrieves active user cart (or creates new cart automatically).
    - `POST /carts/{userId}/items` — Adds item to cart or increments existing quantity.
    - `PUT /carts/{userId}/items/{cartItemId}` — Updates item quantity.
    - `DELETE /carts/{userId}/items/{cartItemId}` — Removes single item from cart.
    - `DELETE /carts/{userId}` — Resets/clears user cart (invoked after order checkout).
  - Added RFC 7807 global exception handling (`GlobalExceptionHandler`).
  - Verified 100% build & unit test pass rate (`CartServiceTest`, `CartServiceApplicationTests`).

### [2026-10-01] — Phase 4 Step 3: Backend Development (inventory-service) Completed
- **Status:** Completed
- **Deliverables:**
  - Implemented Inventory Microservice at [`backend/inventory-service`](file:///run/media/rinos/Data/store.io/backend/inventory-service).
  - Implemented `@Version` optimistic concurrency control and stock reserve/release/deduct saga endpoints.

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
| **Phase 4** | Backend Development | 🟢 `user`, `product`, `inventory`, `cart` Completed / `order`, `payment` Pending |
| **Phase 5** | Postman Testing | ⚪ Pending |
| **Phase 6** | Node.js BFF | ⚪ Pending |
| **Phase 7** | React Frontend | ⚪ Pending |
| **Phase 8** | Dockerization | ⚪ Pending |
| **Phase 9** | Advanced Microservices Features | ⚪ Pending |

# System Requirements Specification (SRS) — store.io

## 1. Executive Overview
`store.io` is a distributed e-commerce microservices platform designed for high concurrency, modular scaling, and clean domain isolation. Each domain is bounded within its own microservice with a dedicated database (Database-per-Service pattern).

---

## 2. Initial Scope & Service Boundaries

We are building 6 core microservices in the initial release phase:

```text
+-----------------------+     +-----------------------+     +-----------------------+
|   User/Auth Service   |     |    Product Service    |     |   Inventory Service   |
|  (Auth, Profile, Role)|     |  (Catalog, Categories)|     | (Stock Level, Hold)   |
+-----------------------+     +-----------------------+     +-----------------------+

+-----------------------+     +-----------------------+     +-----------------------+
|     Cart Service      |     |     Order Service     |     |    Payment Service    |
| (Cart Items, Session) |     | (Checkout, Lifecycle) |     |  (Transactions, Mock) |
+-----------------------+     +-----------------------+     +-----------------------+
```

---

## 3. Functional Requirements (FR)

### 3.1 User & Authentication Service (`user-service`)
* **FR-USR-01: User Registration**: System must allow new users (Customers & Admins) to register with email, password, full name, and role. Email must be unique.
* **FR-USR-02: Password Security**: Passwords must be hashed using BCrypt before storing.
* **FR-USR-03: User Login & Token Generation**: Authenticated users receive a signed Stateless JSON Web Token (JWT) containing userId, email, and roles.
* **FR-USR-04: User Profile Management**: Authenticated users can view and update profile details (name, address, phone).
* **FR-USR-05: Identity Verification**: Expose an endpoint/filter for internal token validation to resolve user identity across microservices.

### 3.2 Product Catalog Service (`product-service`)
* **FR-PRD-01: Product Catalog Browsing**: Public access to list products with pagination, sorting (price, name), and filtering (category, search query).
* **FR-PRD-02: Product Detail View**: Public access to fetch detailed information for a specific product ID.
* **FR-PRD-03: Product Management (Admin)**: Create, update, soft-delete products (Title, Description, SKU, Price, Category, Status).
* **FR-PRD-04: Batch Lookup**: Provide multi-ID fetch endpoint for BFF/Cart Service to resolve product prices and details efficiently in bulk.

### 3.3 Inventory Service (`inventory-service`)
* **FR-INV-01: Stock Tracking**: Maintain available quantity for each product SKU / Product ID.
* **FR-INV-02: Stock Allocation / Deduction**: Deduct available stock upon successful order creation.
* **FR-INV-03: Stock Release**: Restore stock if an order is cancelled or payment fails.
* **FR-INV-04: Low Stock Alerting**: Mark items as out-of-stock when quantity reaches 0.
* **FR-INV-05: Inventory Update (Admin)**: Allow admins to restock or set stock counts.

### 3.4 Cart Service (`cart-service`)
* **FR-CRT-01: Add Item to Cart**: Authenticated user can add a product with quantity to active cart.
* **FR-CRT-02: Update Cart Item Quantity**: Modify quantity or remove items from cart.
* **FR-CRT-03: View Cart**: Fetch user's active cart with calculated subtotal.
* **FR-CRT-04: Clear Cart**: Remove all items from active cart upon order placement or user reset.

### 3.5 Order Service (`order-service`)
* **FR-ORD-01: Order Creation (Checkout)**: Convert active cart items into a pending order with shipping details and snapshot pricing.
* **FR-ORD-02: Price Snapshotting**: Lock item prices at the exact moment of order creation to protect against catalog price changes after checkout.
* **FR-ORD-03: Order State Machine**: Order status lifecycle: `PENDING` -> `PAID` / `PAYMENT_FAILED` -> `CANCELLED` / `SHIPPED` -> `DELIVERED`.
* **FR-ORD-04: Order History**: Users can view their order history and order details.

### 3.6 Payment Service (`payment-service`)
* **FR-PAY-01: Process Payment**: Process simulated payment requests for an order (Credit Card / Mock Gateway).
* **FR-PAY-02: Transaction Audit Log**: Record transaction ID, order ID, amount, payment method, timestamp, and status (`SUCCESS`, `FAILED`, `REFUNDED`).
* **FR-PAY-03: Refund Processing**: Provide refund mechanism for cancelled or failed orders.

---

## 4. Non-Functional Requirements (NFR)

### 4.1 Security & Compliance
* **NFR-SEC-01: Stateless Authentication**: JWT tokens signed using HMAC SHA-256 / RS256 algorithm.
* **NFR-SEC-02: Password Policy**: Min 8 characters, hashed with BCrypt (strength 10+).
* **NFR-SEC-03: Network Isolation**: Microservices databases isolated; no cross-service database access.

### 4.2 Performance & Responsiveness
* **NFR-PRF-01: API Response Time**: Read operations < 100ms (p95); Write operations < 300ms (p95).
* **NFR-PRF-02: Pagination**: All list endpoints default to paginated responses (default size: 20 items).

### 4.3 Scalability & Availability
* **NFR-SCL-01: Loose Coupling**: Services communicate via HTTP REST (and later async event messaging). Failure of Payment service must not crash Product catalog browsing.
* **NFR-SCL-02: Database-per-Service**: Each service owns its database instance/schema exclusively.

### 4.4 Data Integrity & Consistency
* **NFR-DAT-01: Price Lock Integrity**: Orders must store static snapshot prices rather than referencing live product tables.
* **NFR-DAT-02: Inventory Concurrency**: Stock deduction must prevent overselling under concurrent purchase requests (Optimistic / Pessimistic Locking).

---

## 5. Primary User Stories & Business Scenarios

| User Story ID | Persona | Action | Goal / Outcome |
|---|---|---|---|
| **US-01** | Visitor | Register & Login | Receive JWT token to access protected shopping endpoints. |
| **US-02** | Customer | Search & Browse Products | Filter products by category and view price / availability. |
| **US-03** | Customer | Manage Cart | Add items, change quantities, and prepare for checkout. |
| **US-04** | Customer | Checkout Order | Reserve inventory stock and lock item prices in a pending order. |
| **US-05** | Customer | Pay Order | Submit payment; if successful, order status transitions to `PAID`. |
| **US-06** | Admin | Manage Products & Stock | Add new products, update prices, and restock inventory. |

---

## 6. Edge Cases & Risk Mitigation Matrix

| Scenario / Edge Case | Risk | Mitigation Strategy |
|---|---|---|
| Concurrent stock purchase (Flash sale) | Stock goes negative (overselling) | Database level optimistic lock (`@Version`) or pessimistic `SELECT FOR UPDATE` in Inventory service. |
| Product price changes during checkout | Customer charged incorrect amount | Order service captures explicit price snapshots during checkout order creation. |
| Payment Gateway Timeout / Failure | Order stuck in `PENDING` state | Automatic timeout cancellation / compensation transaction to release reserved inventory. |
| Direct Microservice Access Bypass | Unauthorized direct access to backend services | JWT verification at BFF layer and internal service security filters. |

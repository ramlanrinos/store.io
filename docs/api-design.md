# API Specification & Design Document — store.io

## 1. Overview & Standard Conventions

This document specifies all REST API endpoints implemented across the 6 microservices in `store.io`.

### Microservice Base URLs
* **User Service:** `http://localhost:8081`
* **Product Service:** `http://localhost:8082`
* **Inventory Service:** `http://localhost:8083`
* **Cart Service:** `http://localhost:8084`
* **Order Service:** `http://localhost:8085`
* **Payment Service:** `http://localhost:8086`

### Standard Headers
* `Content-Type: application/json`
* `Authorization: Bearer <jwt_token>` (for protected customer endpoints)
* `X-User-Id: <user_id>` (for internal / BFF header propagation)

### Error Response Format (RFC 7807 Problem Details)
All error responses follow this standard schema:

```json
{
  "type": "https://store.io/errors/bad-request",
  "title": "Bad Request",
  "status": 400,
  "detail": "Error message description",
  "instance": "/users/login",
  "timestamp": "2026-10-02T23:00:00Z",
  "errors": {
    "email": "Invalid email format"
  }
}
```

---

## 2. User & Auth Service (`user-service` — Port 8081)

### 2.1 Register User
* **Method:** `POST`
* **URL:** `http://localhost:8081/users/register`
* **Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "email": "john.doe@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+1234567890"
  }
  ```
* **Success Response (`201 Created`):**
  ```json
  {
    "id": 1,
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+1234567890",
    "active": true,
    "roles": ["ROLE_CUSTOMER"],
    "createdAt": "2026-10-02T23:00:00"
  }
  ```

### 2.2 Login User
* **Method:** `POST`
* **URL:** `http://localhost:8081/users/login`
* **Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "email": "john.doe@example.com",
    "password": "password123"
  }
  ```
* **Success Response (`200 OK`):**
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEs...",
    "tokenType": "Bearer",
    "expiresIn": 86400000,
    "userId": 1,
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "roles": ["ROLE_CUSTOMER"]
  }
  ```

### 2.3 Get User Profile
* **Method:** `GET`
* **URL:** `http://localhost:8081/users/profile`
* **Headers:** `Authorization: Bearer <jwt_token>` OR `X-User-Id: 1`
* **Success Response (`200 OK`):**
  ```json
  {
    "id": 1,
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+1234567890",
    "active": true,
    "roles": ["ROLE_CUSTOMER"],
    "createdAt": "2026-10-02T23:00:00"
  }
  ```

### 2.4 Validate JWT Token
* **Method:** `GET`
* **URL:** `http://localhost:8081/users/validate?token=<jwt_token>`
* **Success Response (`200 OK`):**
  ```json
  {
    "valid": true,
    "userId": 1,
    "email": "john.doe@example.com"
  }
  ```

---

## 3. Product Catalog Service (`product-service` — Port 8082)

### 3.1 Create Category
* **Method:** `POST`
* **URL:** `http://localhost:8082/categories`
* **Request Body:**
  ```json
  {
    "name": "Electronics",
    "slug": "electronics",
    "description": "Gadgets & Electronics"
  }
  ```
* **Success Response (`201 Created`):**
  ```json
  {
    "id": 1,
    "name": "Electronics",
    "slug": "electronics",
    "description": "Gadgets & Electronics"
  }
  ```

### 3.2 Create Product
* **Method:** `POST`
* **URL:** `http://localhost:8082/products`
* **Request Body:**
  ```json
  {
    "categoryId": 1,
    "name": "Wireless Noise-Canceling Headphones",
    "sku": "AUD-HEAD-001",
    "description": "Premium Bluetooth headphones",
    "price": 299.99
  }
  ```
* **Success Response (`201 Created`):**
  ```json
  {
    "id": 1,
    "categoryId": 1,
    "categoryName": "Electronics",
    "name": "Wireless Noise-Canceling Headphones",
    "sku": "AUD-HEAD-001",
    "description": "Premium Bluetooth headphones",
    "price": 299.99,
    "active": true,
    "createdAt": "2026-10-02T23:00:00"
  }
  ```

### 3.3 Get Products (Paginated & Search)
* **Method:** `GET`
* **URL:** `http://localhost:8082/products?categoryId=1&search=Headphones&page=0&size=10`
* **Success Response (`200 OK`):**
  ```json
  {
    "content": [
      {
        "id": 1,
        "categoryId": 1,
        "categoryName": "Electronics",
        "name": "Wireless Noise-Canceling Headphones",
        "sku": "AUD-HEAD-001",
        "price": 299.99,
        "active": true
      }
    ],
    "pageNumber": 0,
    "pageSize": 10,
    "totalElements": 1,
    "totalPages": 1,
    "last": true
  }
  ```

### 3.4 Batch Resolve Products
* **Method:** `POST`
* **URL:** `http://localhost:8082/products/batch`
* **Request Body:**
  ```json
  {
    "productIds": [1]
  }
  ```
* **Success Response (`200 OK`):**
  ```json
  [
    {
      "id": 1,
      "name": "Wireless Noise-Canceling Headphones",
      "sku": "AUD-HEAD-001",
      "price": 299.99
    }
  ]
  ```

---

## 4. Inventory Service (`inventory-service` — Port 8083)

### 4.1 Restock Product
* **Method:** `POST`
* **URL:** `http://localhost:8083/inventory/restock`
* **Request Body:**
  ```json
  {
    "productId": 1,
    "sku": "AUD-HEAD-001",
    "quantity": 100,
    "reason": "Initial Warehouse Stock"
  }
  ```
* **Success Response (`201 Created`):**
  ```json
  {
    "id": 1,
    "productId": 1,
    "sku": "AUD-HEAD-001",
    "availableQuantity": 100,
    "reservedQuantity": 0,
    "updatedAt": "2026-10-02T23:00:00"
  }
  ```

### 4.2 Reserve Stock (Checkout)
* **Method:** `POST`
* **URL:** `http://localhost:8083/inventory/reserve`
* **Request Body:**
  ```json
  {
    "orderNumber": "ORD-20261002-1001",
    "items": [
      {
        "productId": 1,
        "quantity": 2
      }
    ]
  }
  ```
* **Success Response (`200 OK`):** Empty Body (`200 OK`)

---

## 5. Cart Service (`cart-service` — Port 8084)

### 5.1 Add Item to Cart
* **Method:** `POST`
* **URL:** `http://localhost:8084/carts/1/items`
* **Request Body:**
  ```json
  {
    "productId": 1,
    "quantity": 2
  }
  ```
* **Success Response (`200 OK`):**
  ```json
  {
    "id": 1,
    "userId": 1,
    "items": [
      {
        "id": 1,
        "productId": 1,
        "quantity": 2,
        "addedAt": "2026-10-02T23:00:00"
      }
    ],
    "totalItemsCount": 2,
    "updatedAt": "2026-10-02T23:00:00"
  }
  ```

### 5.2 Get User Cart
* **Method:** `GET`
* **URL:** `http://localhost:8084/carts/1`
* **Success Response (`200 OK`):** Same schema as 5.1

---

## 6. Order Service (`order-service` — Port 8085)

### 6.1 Create Pending Order
* **Method:** `POST`
* **URL:** `http://localhost:8085/orders`
* **Request Body:**
  ```json
  {
    "userId": 1,
    "shippingAddress": "123 Broadway St, New York, NY 10001",
    "items": [
      {
        "productId": 1,
        "productName": "Wireless Noise-Canceling Headphones",
        "priceSnapshot": 299.99,
        "quantity": 2
      }
    ]
  }
  ```
* **Success Response (`201 Created`):**
  ```json
  {
    "id": 1,
    "orderNumber": "ORD-20261002-A1B2C3",
    "userId": 1,
    "totalAmount": 599.98,
    "status": "PENDING",
    "shippingAddress": "123 Broadway St, New York, NY 10001",
    "items": [
      {
        "id": 1,
        "productId": 1,
        "productName": "Wireless Noise-Canceling Headphones",
        "priceSnapshot": 299.99,
        "quantity": 2,
        "itemSubtotal": 599.98
      }
    ],
    "createdAt": "2026-10-02T23:00:00"
  }
  ```

---

## 7. Payment Service (`payment-service` — Port 8086)

### 7.1 Process Payment
* **Method:** `POST`
* **URL:** `http://localhost:8086/payments/process`
* **Request Body:**
  ```json
  {
    "orderId": 1,
    "amount": 599.98,
    "paymentMethod": "CREDIT_CARD"
  }
  ```
* **Success Response (`201 Created`):**
  ```json
  {
    "id": 1,
    "transactionId": "TXN-9F8E7D6C5B4A",
    "orderId": 1,
    "amount": 599.98,
    "paymentMethod": "CREDIT_CARD",
    "status": "SUCCESS",
    "createdAt": "2026-10-02T23:00:00"
  }
  ```

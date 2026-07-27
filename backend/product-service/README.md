# Product Service

## Overview

The Product Service is responsible for managing product-related information in the Store.io platform.

This service handles:

* Product catalog management
* Product categories
* Product search
* Inventory quantity tracking

---

## Features

### Product Management

* Create products
* View product details
* Update products
* Delete products
* Search products

### Category Management

* Create categories
* View categories
* Update categories
* Delete categories

### Inventory Management

* Track available stock quantity
* Update stock levels

---

## Data Model

### Category

| Field       | Type        | Description                |
| ----------- | ----------- | -------------------------- |
| id          | UUID / Long | Unique category identifier |
| name        | String      | Category name              |
| description | String      | Category description       |

### Product

| Field       | Type        | Description                  |
| ----------- | ----------- | ---------------------------- |
| id          | UUID / Long | Unique product identifier    |
| name        | String      | Product name                 |
| description | String      | Product description          |
| price       | Decimal     | Product price                |
| stock       | Integer     | Available inventory quantity |
| categoryId  | UUID / Long | Associated category          |
| imageUrl    | String      | Product image URL            |
| createdAt   | Timestamp   | Creation date                |
| updatedAt   | Timestamp   | Last update date             |

---

## API Endpoints

### Product APIs

| Method | Endpoint       | Description          |
| ------ | -------------- | -------------------- |
| GET    | /products      | Get all products     |
| GET    | /products/{id} | Get product by ID    |
| POST   | /products      | Create a new product |
| PUT    | /products/{id} | Update product       |
| DELETE | /products/{id} | Delete product       |

### Category APIs

| Method | Endpoint         | Description           |
| ------ | ---------------- | --------------------- |
| GET    | /categories      | Get all categories    |
| GET    | /categories/{id} | Get category by ID    |
| POST   | /categories      | Create a new category |
| PUT    | /categories/{id} | Update category       |
| DELETE | /categories/{id} | Delete category       |

---

## Example Product

```json
{
  "id": 1,
  "name": "iPhone 16",
  "description": "Latest Apple smartphone",
  "price": 999.99,
  "stock": 50,
  "categoryId": 1,
  "imageUrl": "https://cdn.store.io/images/iphone16.jpg",
  "createdAt": "2026-07-27T10:00:00Z",
  "updatedAt": "2026-07-27T10:00:00Z"
}
```

---

## Future Enhancements

Planned for future phases:

* Multiple product images
* Product reviews and ratings
* Product attributes (size, color, brand)
* Inventory reservation
* Product recommendations
* Advanced search and filtering
* Image upload service integration

---

## Service Responsibility

This service owns:

* Products
* Categories
* Product inventory quantities

This service does not own:

* Orders
* Payments
* Users
* Shipping
* Notifications

These responsibilities belong to their respective microservices.

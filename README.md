# spring-mvc-demo

## 📌 Overview

**spring-mvc-demo** is a **Java Web application built with pure Spring MVC (non-Spring Boot)**, designed to demonstrate a **deep understanding of Spring MVC internals**, **enterprise-style architecture**, and **real-world e-commerce business logic**.

The project simulates a **mini e-commerce system** with product management, cart, order processing, authentication, authorization, and centralized exception handling.

This repository is used as a **main portfolio project** to demonstrate:
- Core Spring MVC knowledge (DispatcherServlet, HandlerMapping, HandlerAdapter, ViewResolver)
- Clean layered architecture (Controller – Service – DAO – Domain – View)
- Transaction-safe Cart / Order design
- Production-minded validation, security, and exception handling

---

## 🎯 Project Goals

- Master **Spring MVC core (non-Boot)** instead of relying on auto-configuration
- Understand the **full request–response lifecycle** in Spring MVC
- Apply an **enterprise mindset** when organizing code
- Clearly separate responsibilities across layers
- Design Cart and Order logic similar to real production systems

---

## 🏗️ Architecture Overview

Client (Browser)
↓
DispatcherServlet
↓
HandlerMapping → HandlerAdapter
↓
@Controller
↓
Service Layer (@Transactional)
↓
DAO Layer (Hibernate / JPA)
↓
MySQL


- **View**: JSP + JSTL (Server-Side Rendering)
- **ORM**: Hibernate / JPA
- **Transaction Management**: Spring `@Transactional`

---

## 🧱 Technology Stack

- Java 17
- Spring MVC (Java-based configuration)
- Hibernate / JPA
- JSP / JSTL
- MySQL
- Apache Tomcat 10
- Maven
- SLF4J + Logback

---

## 📂 Project Structure (Simplified)
```
com.demo
├── config # Spring MVC & JPA configuration
├── security # AuthInterceptor, session keys
├── web
│ ├── controller # MVC controllers
│ ├── dto # Form & view DTOs
│ ├── filter # Search / filter objects
│ ├── paging # PageRequest / PageResponse
│ └── util # URL & redirect helpers
├── service # Business logic layer
├── dao # Persistence layer (JPA)
├── entity # Domain entities
├── exception # Custom business exceptions
└── util # Validation helpers

com.demo
├── config # Spring MVC & JPA configuration
├── security # AuthInterceptor, session keys
├── web
│ ├── controller # MVC controllers
│ ├── dto # Form & view DTOs
│ ├── filter # Search / filter objects
│ ├── paging # PageRequest / PageResponse
│ └── util # URL & redirect helpers
├── service # Business logic layer
├── dao # Persistence layer (JPA)
├── entity # Domain entities
├── exception # Custom business exceptions
└── util # Validation helpers```


---

## ✅ Implemented Features

### 🔐 Authentication & Authorization

- Login using email & password
- Session-based authentication
- **AuthInterceptor** protects `/admin/**`, `/cart/**`, `/orders/**`
- Role-based authorization for admin endpoints
- Redirect to login with preserved target URL

---

### 📦 Product Management (Admin)

- Full CRUD for products
- Pagination using enterprise-style paging model (`page`, `size`)
- Sorting & filtering
- **Soft delete** (inactive products instead of physical deletion)
- Form validation:
  - Type mismatch validation
  - Business rule validation

---

### 🛒 Cart & Order Module (Complete)

#### Cart

- Cart is created per user (get-or-create strategy)
- Add products to cart
- Update item quantity
- Remove items from cart
- Clear entire cart
- Stock validation (quantity cannot exceed available stock)
- Cart data persisted in database (not session-only)

#### Order

- Checkout flow: **Cart → Order**
- Persist `Order` and `OrderItem`
- **Price snapshot** stored at checkout time
- Entire checkout process wrapped in **a single transaction**
- Product stock is deducted on checkout
- Order cancellation:
  - Allowed only for valid order status
  - Product stock is restored correctly

➡️ All business logic is implemented **exclusively in the Service layer**

---

### ⚠️ Validation & Exception Handling

- Input validation at Controller level
- Business validation at Service level
- Custom business exceptions:
  - `BadRequestException`
  - `NotFoundException`
  - `ForbiddenException`
  - `ConflictException`

Centralized error handling:
- Security errors (403)
- Business errors (redirect + flash message)
- System errors (500)

---

### 📄 Paging Model (Enterprise-style)

- `PageRequest`: page, size, sort, direction
- `PageResponse`: items, totalItems, totalPages
- JSP views only render data (no business logic in view)

---

## 🧠 Highlights

- No Spring Boot → full control over Spring MVC configuration
- Clear layered architecture with strong separation of concerns
- Transaction-safe Cart & Order design
- Business logic isolated from Controllers
- Centralized exception & redirect strategy
- Solid foundation for migration to **Spring Boot + REST API**

---

## 🚀 Future Improvements

- Refactor to Spring Boot
- Expose RESTful APIs
- Integrate Angular frontend
- Add unit tests for Service and DAO layers

---

## 👨‍💻 Author

**Đặng Quốc Thanh**  
Java Web Developer

> This project was built with the mindset: *understand deeply – implement realistically – design with enterprise standards*.





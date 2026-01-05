# spring-mvc-demo

## 📌 Giới thiệu

**spring-mvc-demo** là một dự án **Java Web sử dụng Spring MVC thuần (không Spring Boot)**, được xây dựng với mục tiêu **hiểu sâu bản chất Spring**, tổ chức code theo **chuẩn enterprise**, và mô phỏng một **mini e-commerce system** hoàn chỉnh.

Dự án này được dùng làm **portfolio chính** để chứng minh năng lực:

* Spring MVC core (DispatcherServlet, HandlerMapping, HandlerAdapter, ViewResolver)
* Thiết kế kiến trúc phân tầng rõ ràng
* Xử lý nghiệp vụ Cart / Order chuẩn transaction
* Exception handling & authentication thực tế

---

## 🎯 Mục tiêu

* Làm chủ **Spring MVC (non-Boot)** thay vì chỉ dùng auto-config
* Hiểu rõ **luồng request-response** trong Spring
* Áp dụng **enterprise mindset** khi tổ chức code
* Tách biệt rõ **Controller – Service – DAO – Domain – View**
* Xây dựng Cart / Order giống hệ thống thực tế

---

## 🏗️ Kiến trúc tổng thể

```
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
```

* **View**: JSP + JSTL (SSR)
* **ORM**: Hibernate / JPA
* **Transaction**: Spring @Transactional

---

## 🧱 Công nghệ sử dụng

* Java 17
* Spring MVC (Java Config)
* Hibernate / JPA
* JSP / JSTL
* MySQL
* Apache Tomcat 10
* Maven
* SLF4J + Logback

---

## 📂 Cấu trúc dự án (rút gọn)

```
com.demo
├── config          # Spring, JPA, Web config
├── security        # AuthInterceptor, session keys
├── web
│   ├── controller  # MVC Controllers
│   ├── dto         # Form / View DTO
│   ├── filter      # Filter objects (search, paging)
│   ├── paging      # PageRequest / PageResponse
│   └── util        # Url, Redirect helpers
├── service         # Business logic
├── dao             # Persistence layer
├── entity          # JPA entities
├── exception       # Custom business exceptions
└── util            # Validation helpers
```

---

## ✅ Chức năng đã hoàn thành

### 🔐 Authentication & Authorization

* Login bằng email / password
* Lưu session người dùng
* **AuthInterceptor** bảo vệ `/admin/**`
* Redirect về login kèm `next URL`

---

### 📦 Product Management (Admin)

* CRUD Product
* Phân trang chuẩn enterprise (`page`, `size`)
* Sorting & filtering
* Form validation (type mismatch + business rule)

---

### 🛒 Cart & Order Module (HOÀN CHỈNH)

#### Cart

* Tạo cart theo user (get-or-create)
* Thêm sản phẩm vào cart
* Cập nhật số lượng
* Xóa item khỏi cart
* Validate tồn kho (không vượt stock)
* Tính tổng số lượng & tổng tiền

#### Order

* Checkout: **Cart → Order**
* Persist `Order` & `OrderItem`
* Gói toàn bộ trong **1 transaction**

➡️ Logic nghiệp vụ nằm **100% ở Service layer**

---

### ⚠️ Validation & Exception Handling

* Validate input tại Controller
* Validate nghiệp vụ tại Service
* Custom exception:

  * `BadRequestException`
  * `NotFoundException`
  * `ForbiddenException`
  * `ConflictException`
* Xử lý lỗi tập trung, phân biệt:

  * Lỗi bảo mật (403)
  * Lỗi nghiệp vụ (redirect + flash message)

---

### 📄 Paging Model (Enterprise-style)

* `PageRequest`: page, size, sort, dir
* `PageResponse`: items, totalItems, totalPages
* JSP chỉ render dữ liệu, không chứa logic

---

## 🧠 Điểm nổi bật

* Không dùng Spring Boot → hiểu rõ Spring MVC core
* Kiến trúc rõ ràng, dễ mở rộng
* Cart / Order xử lý đúng transaction
* Exception & validation chuẩn production mindset
* Phù hợp làm nền để nâng cấp lên **Spring Boot + REST API**

---

## 🚀 Hướng phát triển tiếp theo

* Refactor sang Spring Boot
* Expose REST API
* Kết nối Angular frontend
* Viết unit test cho Service / DAO

---

## 👨‍💻 Tác giả

**Đặng Quốc Thanh**
Java Web Developer

> Project được xây dựng với mục tiêu *hiểu sâu – làm thật – code chuẩn enterprise*.

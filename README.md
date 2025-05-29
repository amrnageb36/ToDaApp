# 🗂️ ToDa App Backend

A microservices-based backend application for managing ToDo tasks with secure user authentication and authorization using JWT. This project uses **Spring Boot**, **MySQL**, **JUnit5**, and follows REST API best practices. The backend is split into two main microservices: **ToDo Service** and **User Service**.

---

## 📦 Project Structure

### 1. ✅ ToDo Service

Handles all CRUD operations for ToDo items associated with a user.

#### 📌 Database Schema

**Items Table**
- `id` (PK)
- `title`
- `user_id` (FK)
- `item_details_id` (FK)

**Item_Details Table**
- `id` (PK)
- `description`
- `created_at`
- `priority`
- `status`

#### 🛠 Features
- Add, delete, update, and search ToDo items by title or ID.
- Each API checks token validity via `/checkToken` endpoint from User Service.
- Swagger documentation for all APIs.
- Global Exception Handling (e.g., NotFound).
- Input validation.
- Unit Testing using **JUnit5**.
- Postman Collection for API testing.

#### 📡 APIs

| Endpoint       | Method | Auth Header | Description              |
|----------------|--------|-------------|--------------------------|
| `/add`         | POST   | JWT         | Add a new ToDo item      |
| `/delete/{id}` | DELETE | JWT         | Delete an item by ID     |
| `/update/{id}` | PUT    | JWT         | Update an item by ID     |
| `/search/{id}` | GET    | JWT         | Search for an item by ID |

---

### 2. 👤 User Service

Manages user registration, authentication, authorization, and password recovery via email OTP.

#### 📌 Database Schema

**User Table**
- `id` (PK)
- `email`
- `password`
- `enabled`

**Otp Table**
- `id` (PK)
- `OTP`
- `expiration_time`
- `user_id` (FK)

**Jwt Table**
- `id` (PK)
- `token`
- `user_id` (FK)
- `created_at`
- `expiration_date`
- `token_type`

#### 🛠 Features
- Register and activate users.
- Login and JWT generation.
- Token validation and extraction.
- Password reset via OTP sent to email (JavaMail).
- Swagger API documentation.
- Postman Collection for API testing.

#### 🔐 Authentication APIs

| Endpoint                  | Method | Header     | Description                                                  |
|---------------------------|--------|------------|--------------------------------------------------------------|
| `/login`                  | POST   | -          | Login and get JWT token                                      |
| `/register`               | POST   | -          | Register new user and send OTP                              |
| `/activate?username`      | POST   | -          | Activate user with OTP                                      |
| `/checkToken`             | POST   | JWT        | Check validity of JWT token                                 |
| `/forgetPassword`         | POST   | JWT        | Send OTP to registered email for password reset             |
| `/changePassword`         | POST   | JWT, OTP   | Change password after OTP verification                      |
| `/regenrateOtp?email`     | POST   | -          | Regenerate and resend OTP                                   |

#### 👥 User Management APIs

| Endpoint       | Method | Auth Header | Description              |
|----------------|--------|-------------|--------------------------|
| `/delete`      | DELETE | JWT         | Delete user account      |
| `/update`      | PUT    | JWT         | Update user details      |

--

## 🔧 Tech Stack

| Technology       | Usage                    |
|------------------|--------------------------|
| Spring Boot      | Backend Framework        |
| MySQL            | Database                 |
| JPA/Hibernate    | ORM                      |
| JUnit5           | Testing                  |
| Swagger          | API Documentation        |
| Postman          | API Testing              |
| Java Mail API    | Sending OTPs via Email   |

---


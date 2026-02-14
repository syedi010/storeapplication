# StoreApplication
StoreApplication is a Spring Boot REST API for managing a store system.
It allows management of categories, products, and users using a layered architecture with MySQL database integration.
## 🚀 Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- Git & GitHub Actions (CI)
## ✨ Features

- Add & fetch categories
- Detect unique and duplicate category names
- Add & fetch products
- User registration and retrieval
- RESTful API structure
- CI/CD pipeline with GitHub Actions
## 📂 Project Structure

Controller → Service → Repository → Database
src/main/java/com/store
 ├── controller
 ├── service
 ├── repository
 ├── entity
## 🗄 Database Configuration

Make sure MySQL is running.

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/storedb
spring.datasource.username=root
spring.datasource.password=your_password
## ▶ How to Run

1. Clone the repository
2. Open in IDE
3. Configure MySQL
4. Run:

mvn clean install
mvn spring-boot:run
## ⚙ CI/CD

This project uses GitHub Actions to automatically build the application on every push to the main branch.

## 📌 Author
Developed by Syedi

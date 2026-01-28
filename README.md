# User CRUD API

A simple RESTful API for managing users, built with Spring Boot, H2 Database, and implementing a layered architecture.

## 🚀 Technologies
- **Java 21**
- **Spring Boot 3.4.2**
- **Spring Data JPA**
- **H2 Database** (In-Memory)
- **Lombok**
- **Maven**

## 📋 Prerequisites
- JDK 21 installed (`java -version`)
- Git installed

## 🛠️ Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/dwisusanto007/java-user-crud-api.git
   cd java-user-crud-api
   ```

2. **Build the project**
   ```bash
   ./mvnw clean package
   ```

## ▶️ Running the Application

Run the application using the Maven wrapper:
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`.

## 🔌 API Endpoints

| Method | Endpoint | Description | Request Body Example |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/users` | List all users | - |
| `GET` | `/api/users/{id}` | Get user by ID | - |
| `POST` | `/api/users` | Create a new user | `{"name": "John", "email": "john@example.com"}` |
| `PUT` | `/api/users/{id}` | Update a user | `{"name": "Jane", "email": "jane@example.com"}` |
| `DELETE` | `/api/users/{id}` | Delete a user | - |

## 📚 API Documentation (Swagger)
The API documentation is available via Swagger UI:
- **URL:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

> **Note:** If port 8080 is in use, check the console output for the actual port (e.g., 8081).

## 📝 Logging
Application logs are written to a file named `app.log` in the root directory.
- **Log File:** `./app.log`
- **Configuration:** `logging.file.name=app.log` in `application.properties`

## 🧪 Running Tests

To run the automated tests (Unit & Integration):
```bash
./mvnw clean test
```

## 📂 Project Structure
```
src/main/java/com/antigravity/user_api/
├── controller/       # Web Layer (API Endpoints)
├── service/          # Business Logic
├── repository/       # Data Access Layer
├── entity/           # Database Models
├── dto/              # Data Transfer Objects
└── exception/        # Global Exception Handling
```

## 🗄️ Database Console
Since this project uses H2 In-Memory database, you can access the database console at:
- **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password`

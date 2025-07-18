# Library Management System

A Spring Boot-based Library Management System for managing users, books, and borrowing operations. The system provides a RESTful API for authentication, user management, and book management, supporting advanced search and pagination features.

## Features
- User registration, authentication (JWT-based), and role-based access control (Admin, Librarian, User)
- Book management: add, list, retrieve, and delete books
- Borrowing and returning books
- Pageable and filterable endpoints for users and books
- In-memory H2 database for development and testing
- OpenAPI/Swagger documentation

## Getting Started

### Prerequisites
- Java 21 or higher (project uses Java 24 in pom.xml, but ensure compatibility)
- Maven 3.8+

### Setup
1. **Clone the repository:**
   ```bash
   git clone <repo-url>
   cd library-management-system
   ```
2. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on [http://localhost:8080](http://localhost:8080).

3. **Access H2 Console (for development):**
   - URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - JDBC URL: `jdbc:h2:mem:testdb`
   - User: `sa` (no password)

4. **API Documentation:**
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Overview

### Authentication
- `POST /api/v1/auth/login` — User login (returns JWT)
- `POST /api/v1/auth/register` — User registration

### Users
- `GET /api/v1/users` — List all users (Admin/Librarian only, pageable)
- `GET /api/v1/users/{userId}` — Get user by ID
- `DELETE /api/v1/users/{userId}` — Delete user (Admin only)
- `GET /api/v1/users/{userId}/books` — List all books borrowed by a user
- `GET /api/v1/users/{userId}/books/borrowed` — List current borrowed books by a user
- `PUT /api/v1/users/{userId}/borrow/books/{bookId}` — Borrow a book for a user
- `PUT /api/v1/users/{userId}/return/books/{bookId}` — Return a borrowed book

### Books
- `GET /api/v1/books` — List all books (pageable)
- `POST /api/v1/books` — Add a new book (Librarian/Admin only)
- `GET /api/v1/books/{id}` — Get book by ID
- `DELETE /api/v1/books/{id}` — Delete book by ID (Librarian/Admin only)

## Pagination, Sorting, and Filtering

Endpoints returning a `Page<T>` (such as `/api/v1/users` and `/api/v1/books`) support advanced pagination, sorting, and filtering via query parameters:

- `page` (int): Page number (default: 0)
- `size` (int): Page size (default: 5)
- `sortList` (JSON-encoded, URL-encoded): List of sort objects
- `queryExpressions` (JSON-encoded, URL-encoded): List of query expressions for filtering

### Example: Sorting and Filtering

To sort by `name` ascending and filter users whose email contains `@example.com`:

```http
GET /api/v1/users?sortList=%5B%7B%22by%22%3A%22name%22%2C%22direction%22%3A%22asc%22%7D%5D&queryExpressions=%5B%7B%22parameter%22%3A%22email%22%2C%22condition%22%3A%22LIKE%22%2C%22value%22%3A%22%25@example.com%25%22%7D%5D
```

#### Sort Object
```json
{
  "by": "name",
  "direction": "asc"
}
```

#### QueryExpression Object
```json
{
  "parameter": "email",
  "condition": "LIKE",
  "value": "%@example.com%"
}
```

- **Note:** Both `sortList` and `queryExpressions` must be JSON arrays, then URL-encoded for use as query parameters.
- **Supported Query Conditions:** EQUALS, NOT_EQUALS, GREATER_THAN, GREATER_THAN_OR_EQUALS, LESS_THAN, LESS_THAN_OR_EQUALS, LIKE, NOT_LIKE, IN, NOT_IN, IS_NULL, IS_NOT_NULL, BETWEEN, NOT_BETWEEN.

## Configuration
- All configuration is in `src/main/resources/application.properties`.
- Uses in-memory H2 database by default.
- JWT secret and expiration are set in properties file.

## Testing
Run tests with:
```bash
./mvnw test
```

## License
MIT
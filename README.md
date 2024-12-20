# Order Service

The **Order Service** is a core microservice in the Online Bookstore system responsible for managing customer orders. It interacts with the **Catalog Service** to verify stock availability and tracks the details and status of orders.

---

## Features

- **Place Orders**: Create orders for books with specified quantities.
- **Track Orders**: Retrieve details of individual orders or all orders for a specific user.
- **Manage Order Status**: Supports statuses like `PENDING`, `CONFIRMED`, and `CANCELLED`.

---

## Technologies Used

- **Java**: Programming language.
- **Spring Boot**: Framework for building the RESTful service.
- **Spring Data JPA**: For database interactions.
- **Spring Web**: For building RESTful APIs.
- **PostgreSQL**: Database for storing orders and order items.
- **Maven**: Dependency and build management.
- **MapStruct**: For mapping between entities and DTOs.
- **Lombok**: For reducing boilerplate code.
- **GitHub Actions**: For CI/CD pipeline.
- **Feign Client**: For communicating with other services.
- **RabbitMQ**: For asynchronous communication.
---

## API Endpoints

| Method | Endpoint                     | Description                                 |
|--------|------------------------------|---------------------------------------------|
| POST   | `/orders`                    | Create a new order.                        |
| GET    | `/orders/{id}`               | Retrieve details of a specific order.      |
| GET    | `/users/{userId}/orders`     | Retrieve all orders for a specific user.   |

---

## Database Design

### Order Table
| Column        | Type          | Description                         |
|---------------|---------------|-------------------------------------|
| `id`          | BIGINT        | Primary key.                        |
| `user_id`     | BIGINT        | ID of the user placing the order.   |
| `total_price` | DECIMAL(10,2) | Total cost of the order.            |
| `status`      | VARCHAR       | Status of the order (`PENDING`, etc.). |
| `created_at`  | TIMESTAMP     | Order creation timestamp.           |

### Order Items Table
| Column        | Type          | Description                         |
|---------------|---------------|-------------------------------------|
| `id`          | BIGINT        | Primary key.                        |
| `order_id`    | BIGINT        | Foreign key referencing `orders`.   |
| `book_id`     | BIGINT        | ID of the book being ordered.       |
| `book_title`  | VARCHAR       | Title of the book.                  |
| `quantity`    | BIGINT        | Quantity of the book ordered.       |
| `price`       | DECIMAL(10,2) | Price of the book at order time.    |

---

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- PostgreSQL
- Mapstruct
- Lombok
- Spring Boot
- Spring Data JPA
- Spring Web
- Feign Client
- RabbitMQ

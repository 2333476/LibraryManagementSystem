# Library Management System — Microservices

Multi-module Spring Boot project with:

- `book-service` (PostgreSQL)
- `customers-service` (MySQL)
- `employee-service` (MySQL)
- `loan-service` (Aggregator, MongoDB)
- `api-gateway-service` (Spring Gateway)

## Tech
- Java + Spring Boot (Gradle, Kotlin DSL)
- Docker / Docker Compose
- JaCoCo test coverage
- API Gateway routes to book, customer, employee, and loan services

## Features
- Full **CRUD** implemented for Books, Customers, Employees, and Loans
- Aggregation in `loan-service` (reads from other services)
- Centralized routing through `api-gateway-service`

## Run locally
```bash
./gradlew clean build
docker compose up -d

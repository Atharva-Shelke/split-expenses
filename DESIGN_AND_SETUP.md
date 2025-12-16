# Design and Setup Guide

This document covers the **system design, architecture, and setup instructions** for the SplitExpenses application. It is intended for developers, reviewers, and anyone who wants a deeper technical understanding of the project.

---

## Architecture Overview

The application follows a **layered, enterprise-style architecture** with a clear separation of concerns between presentation, orchestration, and data layers.

```
[ Angular Frontend ]
        |
        v
[ Spring Boot REST API ]
        |
        v
[ Oracle Database (Stored Procedures & Triggers) ]
```

---

## Frontend Design (Angular)

* Component-based UI architecture
* Services handle all API communication
* Route guards enforce authentication and authorization
* Shared services used for state and cross-component data sharing

Design principles:

* No financial or settlement business logic in UI
* UI is driven entirely by backend responses

---

## Backend Design (Spring Boot)

* REST controllers expose HTTP endpoints
* Service layer orchestrates requests and performs validation
* DAO layer interacts with database using JDBC
* RowMapper classes map SQL result sets to domain objects

Key characteristics:

* No SQL in controllers
* Business rules are not duplicated in Java
* Backend acts as a coordination layer between UI and DB

---

## Database Design

* Core business logic implemented using stored procedures
* Triggers enforce data consistency and automatic updates
* Maker–Checker workflow implemented for settlements
* Database owns transactional integrity

This approach ensures:

* Strong consistency
* Centralized business rules
* Reduced risk of partial updates

---

## Key Design Decisions

* Business logic placed close to data for transactional safety
* Thin REST layer to avoid logic duplication
* Frontend kept independent of domain rules
* Explicit maker–checker approval for settlements

---

## Setup Instructions

### 1. Database Setup

1. Create a schema/user in Oracle Database
2. Execute scripts in the following order:

```
database/Alter/version_1.sql
database/SP/*.sql
database/Trigger/*.sql
```

3. Ensure all procedures and triggers compile successfully

---

### 2. Backend Setup

```bash
cd backend
```

Edit configuration:

```
src/main/resources/application.properties
```

Provide:

* Database URL
* Username
* Password

Build and run:

```bash
mvn clean package
mvn spring-boot:run
```

Backend runs on:

```
http://localhost:8080
```

---

### 3. Frontend Setup

```bash
cd frontend
npm install
npm start
```

Frontend runs on:

```
http://localhost:4200
```

Ensure backend URL is correctly configured in:

```
src/environments/environment.ts
```

---

## Setup Completed
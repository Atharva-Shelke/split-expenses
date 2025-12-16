# SplitExpenses

A full-stack group expense management application designed to manage shared expenses, settlements, and balances among members.

---

## Tech Stack

### Backend

* Java 17+
* Spring Boot
* JDBC / DAO pattern
* Oracle Database (Stored Procedures & Triggers)
* Maven

### Frontend

* Angular
* TypeScript
* HTML / CSS

### Database

* Oracle SQL
* Stored Procedures
* Triggers

---

## Project Structure

```
split-expenses/
│
├── backend/      # Spring Boot backend
├── frontend/     # Angular frontend
├── database/     # SQL scripts (SPs, Triggers, Alter scripts)
└── .gitignore
```

---

## Prerequisites

Ensure the following are installed:

* Java 8 or higher (Java 11 / 17 recommended)
* Maven
* Node.js (v18+ recommended)
* npm
* Oracle Database
* Git

---

## Application Features

* User authentication
* Group creation and management
* Add and view expenses
* Split expenses among members
* Settlements between users
* Maker–Checker settlement approval workflow
* Balance calculation

---

## Author

Developed as a full-stack learning project to demonstrate backend, frontend, and database integration.
For detailed architecture and setup instructions, see DESIGN_AND_SETUP.md.

# 🏖️ Ocean View Resort - Room Reservation System

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-10-blue.svg)](https://jakarta.ee/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-Academic-green.svg)]()

> **A comprehensive web-based room reservation system built with Jakarta EE, demonstrating enterprise Java patterns and
best practices.**

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Architecture](#-architecture)
- [Design Patterns](#-design-patterns)
- [Installation](#-installation)
- [Database Setup](#-database-setup)
- [Usage](#-usage)
- [Testing](#-testing)
- [Project Structure](#-project-structure)
- [Version History](#-version-history)
- [Academic Context](#-academic-context)

---

## 🎯 Project Overview

Ocean View Resort is a popular beachside hotel in Galle, Sri Lanka. This system replaces manual reservation management
with a modern, web-based solution that:

- Eliminates booking conflicts
- Reduces processing delays
- Provides efficient record management
- Generates automated billing

Each guest receives a **unique reservation number**, and the system manages all booking details from check-in to
billing.

---

## ✨ Features

### Core Functionality

| Feature                    | Description                              |
|----------------------------|------------------------------------------|
| 🔐 **User Authentication** | Secure login with session management     |
| ➕ **Add Reservation**      | Create new bookings with validation      |
| 🔍 **View Reservation**    | Search and display booking details       |
| 💰 **Calculate Bill**      | Automatic billing based on stay duration |
| ❓ **Help System**          | Comprehensive user guide for staff       |
| 🚪 **Logout**              | Secure session termination               |

### Additional Features

- **Duplicate Prevention**: Prevents double-booking
- **Date Validation**: Ensures logical check-in/check-out dates
- **Input Sanitization**: XSS and SQL injection protection
- **Responsive Design**: Tailwind CSS for modern UI
- **Error Handling**: User-friendly error messages

---

## 🛠️ Technology Stack

### Backend

- **Java 17** - Core programming language
- **Jakarta EE 10** - Enterprise framework (Servlets, JSP, Filters)
- **JDBC** - Database connectivity (Pure JDBC, NO ORM)
- **MySQL 8.0** - Relational database
- **Maven** - Build automation

### Frontend

- **JSP** - Server-side rendering
- **Tailwind CSS** - Utility-first CSS (via CDN)
- **Font Awesome** - Icon library (via CDN)
- **JavaScript** - Client-side validation

### Testing

- **JUnit 5** - Unit and integration testing
- **3 Testing Strategies**: Unit, Integration, Validation

### Development Tools

- **IntelliJ IDEA Ultimate** / **NetBeans**
- **Git** - Version control
- **GitHub** - Repository hosting

---

## 🏗️ Architecture

This application follows a **strict 3-tier architecture**:

```
┌─────────────────────────────────────────┐
│     PRESENTATION LAYER (Tier 1)         │
│  JSP + HTML + CSS + JavaScript          │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│   BUSINESS LOGIC LAYER (Tier 2)         │
│  Servlets + Services + Filters          │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│    DATA ACCESS LAYER (Tier 3)           │
│  DAO Pattern + JDBC + MySQL             │
└─────────────────────────────────────────┘
```

---

## 🎨 Design Patterns

This project demonstrates **5 essential design patterns**:

| Pattern       | Implementation             | Purpose                    |
|---------------|----------------------------|----------------------------|
| **MVC**       | Servlet → JSP → Model      | Separation of concerns     |
| **DAO**       | Interface → Implementation | Data access abstraction    |
| **Singleton** | DatabaseConnection         | Connection pool management |
| **Factory**   | DAOFactory, ServiceFactory | Object creation            |
| **Strategy**  | PricingStrategy            | Dynamic pricing algorithms |

---

## 🚀 Installation

### Prerequisites

- Java JDK 17 or higher
- Apache Tomcat 10.1+ or compatible Jakarta EE server
- MySQL 8.0+
- Maven 3.8+
- Git

### Step 1: Clone Repository

```bash
git clone https://github.com/yourusername/OceanViewRoomReservationSystem.git
cd OceanViewRoomReservationSystem
```

### Step 2: Build Project

```bash
mvn clean install
```

### Step 3: Configure Database

1. Create MySQL database:

```sql
CREATE DATABASE oceanview_db;
```

2. Run SQL scripts:

```bash
mysql -u root -p oceanview_db < database/schema.sql
mysql -u root -p oceanview_db < database/data.sql
```

3. Update database credentials in `DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/oceanview_db";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### Step 4: Deploy to Tomcat

1. Copy WAR file to Tomcat webapps:

```bash
cp target/oceanview-room-reservation-system-1.0-SNAPSHOT.war $TOMCAT_HOME/webapps/
```

2. Start Tomcat server

3. Access application: `http://localhost:8080/oceanview-room-reservation-system/`

---

## 💾 Database Setup

### Schema Overview

**Tables:**

- `users` - System users (staff credentials)
- `room_types` - Room categories and pricing
- `reservations` - Guest booking records
- `bills` - Payment records

### Default Login Credentials

```
Username: admin
Password: admin123
```

> ⚠️ **Security Note**: Change default credentials in production!

---

## 📖 Usage

### Login

1. Navigate to application URL
2. Enter username and password
3. Click "Login"

### Add Reservation

1. From dashboard, click "Add New Reservation"
2. Fill in guest details:
    - Guest Name
    - Address
    - Contact Number
    - Room Type
    - Check-in Date
    - Check-out Date
3. Click "Save Reservation"
4. System generates unique reservation number

### View Reservation

1. Click "View Reservation"
2. Enter reservation number
3. View complete booking details

### Calculate Bill

1. Click "Generate Bill"
2. Enter reservation number
3. System calculates:
    - Number of nights
    - Room rate
    - Total amount
4. Print or save bill

---

## 🧪 Testing

### Run All Tests

```bash
mvn test
```

### Test Coverage

- **40+ Test Cases** covering:
    - DAO operations
    - Service layer logic
    - Validation rules
    - Date calculations
    - Authentication flows

### Testing Strategies

1. **Unit Testing**: Individual component testing
2. **Integration Testing**: Database interaction testing
3. **Validation Testing**: Boundary and edge cases

### Example Test Scenarios

- ✅ Valid login credentials
- ❌ Invalid password
- ❌ Empty username
- ✅ Valid date range
- ❌ Check-out before check-in
- ❌ Duplicate reservation number
- ✅ Bill calculation accuracy

---

## 📂 Project Structure

```
OceanViewRoomReservationSystem/
├── src/
│   ├── main/
│   │   ├── java/lk/icbt/oceanview/oceanviewroomreservationsystem/
│   │   │   ├── model/          # Entity classes (encapsulation)
│   │   │   ├── dao/            # DAO interfaces (abstraction)
│   │   │   ├── dao/impl/       # DAO implementations (polymorphism)
│   │   │   ├── service/        # Business logic interfaces
│   │   │   ├── service/impl/   # Service implementations
│   │   │   ├── servlet/        # Controllers (MVC)
│   │   │   ├── filter/         # Authentication filters
│   │   │   ├── util/           # Utilities (Singleton)
│   │   │   ├── factory/        # Factory pattern
│   │   │   ├── strategy/       # Strategy pattern
│   │   │   └── exception/      # Custom exceptions
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── views/      # JSP pages
│   │       │   └── web.xml     # Deployment descriptor
│   │       ├── css/            # Custom styles
│   │       └── js/             # Client validation
│   └── test/                   # JUnit test cases
├── database/                   # SQL scripts
├── docs/                       # Documentation
├── pom.xml                     # Maven configuration
└── README.md                   # This file
```

See [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) for detailed architecture documentation.

---

## 📝 Version History

### Version 1.0.0 (January 2026)

- ✅ Initial project structure
- ✅ 3-tier architecture implementation
- ✅ MVC pattern with Servlets and JSP
- ✅ DAO pattern with JDBC
- ✅ Singleton pattern for database connection
- ✅ Factory pattern for object creation
- ✅ Strategy pattern for pricing
- ✅ User authentication and session management
- ✅ CRUD operations for reservations
- ✅ Bill calculation and generation
- ✅ Client-side and server-side validation
- ✅ JUnit test suite (40+ cases)
- ✅ Responsive UI with Tailwind CSS

---

## 🎓 Academic Context

### Course Information

- **Course**: CIS6003 Advanced Programming
- **Assessment**: WRIT1 - Web Application Development
- **Institution**: ICBT Campus
- **Target Grade**: 70-100 (Distinction)

### Learning Outcomes Demonstrated

1. ✅ **Object-Oriented Programming**
    - Encapsulation
    - Inheritance
    - Polymorphism
    - Abstraction
    - Method Overloading
    - Method Overriding

2. ✅ **Design Patterns**
    - MVC Pattern
    - DAO Pattern
    - Singleton Pattern
    - Factory Pattern
    - Strategy Pattern

3. ✅ **Enterprise Java Development**
    - Jakarta EE Servlets
    - JSP (JavaServer Pages)
    - Filters
    - Session Management
    - JDBC

4. ✅ **Software Engineering Practices**
    - 3-tier architecture
    - Separation of concerns
    - Code organization
    - Testing strategies
    - Version control (Git)

5. ✅ **Database Design**
    - Normalization
    - Primary/Foreign keys
    - Constraints
    - SQL scripting

---

## 📄 License

This project is developed for academic purposes as part of the CIS6003 Advanced Programming course at ICBT Campus.

---

## 👤 Author

**Guruge Poorna Udara Dhananjaya Perera**  
Student ID: CL/BSCSD/33/76  
Course: CIS6003 Advanced Programming  
Date: March 2026

---

## 📞 Support

For academic inquiries or technical support:

- Email: pooorna84@gmail.com
- GitHub Issues: [Create an issue](https://github.com/yourusername/OceanViewRoomReservationSystem/issues)

---

## 🙏 Acknowledgments

- Course instructor and tutors
- Jakarta EE documentation
- MySQL community
- Tailwind CSS team

---

**⭐ If this project helps you, please consider giving it a star!**

---

*Last Updated: March 1, 2026*

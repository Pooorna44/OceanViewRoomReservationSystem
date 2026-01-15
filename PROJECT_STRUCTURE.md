# Ocean View Room Reservation System - Project Structure

## Academic Context

**Course**: CIS6003 Advanced Programming  
**Assessment**: WRIT1 - Web Application Development  
**Target Grade**: 70-100 (Full Marks)

---

## Architecture Overview

This project implements a **strict 3-tier architecture** following enterprise Java standards:

```
┌─────────────────────────────────────────────────────┐
│         PRESENTATION LAYER (Tier 1)                 │
│  JSP Views + JavaScript + Tailwind CSS (CDN)        │
│  - login.jsp, dashboard.jsp, add-reservation.jsp    │
│  - view-reservation.jsp, bill.jsp, help.jsp         │
└─────────────────────────────────────────────────────┘
                      ↓ HTTP Request/Response
┌─────────────────────────────────────────────────────┐
│      BUSINESS LOGIC LAYER (Tier 2)                  │
│  Servlets (Controllers) + Services + Filters         │
│  - LoginServlet, ReservationServlet, BillServlet    │
│  - AuthenticationFilter (Session Management)         │
│  - Service Layer (Business Rules & Validation)      │
└─────────────────────────────────────────────────────┘
                      ↓ JDBC Calls
┌─────────────────────────────────────────────────────┐
│         DATA ACCESS LAYER (Tier 3)                  │
│  DAO Pattern + JDBC + MySQL                          │
│  - UserDAO, ReservationDAO, RoomTypeDAO             │
│  - Pure JDBC (NO ORM/Hibernate)                     │
└─────────────────────────────────────────────────────┘
```

---

## Package Structure & Design Patterns

### 📦 `model` (Entity/Domain Layer)

**Purpose**: Data representation with proper encapsulation  
**OOP Concepts**: Encapsulation, Inheritance, Abstraction

**Classes**:

- `User.java` - User credentials and profile
- `Reservation.java` - Guest reservation details
- `RoomType.java` - Room categories and pricing
- `Bill.java` - Bill calculation model
- `BaseEntity.java` (Abstract) - Common entity properties

**Demonstrates**:

- ✅ Encapsulation (private fields, getters/setters)
- ✅ Inheritance (BaseEntity as parent class)
- ✅ Abstraction (abstract base class)

---

### 📦 `dao` + `dao.impl` (Data Access Object Pattern)

**Purpose**: Database operations abstraction  
**Design Pattern**: DAO Pattern  
**OOP Concepts**: Abstraction, Polymorphism, Interface-based design

**Interfaces**:

- `UserDAO.java`
- `ReservationDAO.java`
- `RoomTypeDAO.java`

**Implementations** (dao.impl):

- `UserDAOImpl.java`
- `ReservationDAOImpl.java`
- `RoomTypeDAOImpl.java`

**Demonstrates**:

- ✅ DAO Pattern (separation of data access logic)
- ✅ Abstraction (interface contracts)
- ✅ Polymorphism (interface → implementation)

---

### 📦 `service` + `service.impl` (Business Logic Layer)

**Purpose**: Business rules, validations, calculations  
**Design Pattern**: Strategy Pattern (for pricing calculations)  
**OOP Concepts**: Method Overloading, Business Logic Encapsulation

**Interfaces**:

- `AuthenticationService.java`
- `ReservationService.java`
- `BillingService.java`

**Implementations**:

- `AuthenticationServiceImpl.java`
- `ReservationServiceImpl.java`
- `BillingServiceImpl.java`

**Demonstrates**:

- ✅ Business logic separation
- ✅ Validation rules (dates, duplicates, input)
- ✅ Method Overloading (multiple validation methods)

---

### 📦 `servlet` (Controller Layer - MVC)

**Purpose**: Handle HTTP requests and responses  
**Design Pattern**: MVC Pattern (Controllers)  
**Jakarta EE**: Servlet API

**Classes**:

- `LoginServlet.java` - User authentication
- `DashboardServlet.java` - Main menu
- `ReservationServlet.java` - Add/View/Update reservations
- `BillServlet.java` - Calculate and display bills
- `HelpServlet.java` - Help documentation
- `LogoutServlet.java` - Session invalidation

**Demonstrates**:

- ✅ MVC Pattern (Controller layer)
- ✅ Request handling (doGet, doPost)
- ✅ Method Overriding (HttpServlet methods)

---

### 📦 `filter` (Security & Session Management)

**Purpose**: Authentication and authorization  
**Jakarta EE**: Filter API

**Classes**:

- `AuthenticationFilter.java` - Session-based authentication
- `SecurityFilter.java` - XSS/CSRF protection

**Demonstrates**:

- ✅ Filter chain pattern
- ✅ Session management
- ✅ Security best practices

---

### 📦 `util` (Utility Classes)

**Purpose**: Reusable utility components  
**Design Pattern**: Singleton Pattern

**Classes**:

- `DatabaseConnection.java` (Singleton) - MySQL connection pool
- `DateValidator.java` - Date validation utilities
- `InputSanitizer.java` - Input security
- `PasswordUtil.java` - Password hashing

**Demonstrates**:

- ✅ Singleton Pattern (DatabaseConnection)
- ✅ Utility methods
- ✅ Code reusability

---

### 📦 `factory` (Factory Pattern)

**Purpose**: Object creation abstraction  
**Design Pattern**: Factory Pattern

**Classes**:

- `DAOFactory.java` - Creates DAO instances
- `ServiceFactory.java` - Creates Service instances

**Demonstrates**:

- ✅ Factory Pattern
- ✅ Loose coupling
- ✅ Object creation abstraction

---

### 📦 `strategy` (Strategy Pattern)

**Purpose**: Runtime algorithm selection  
**Design Pattern**: Strategy Pattern

**Classes**:

- `PricingStrategy.java` (Interface)
- `StandardPricingStrategy.java`
- `SeasonalPricingStrategy.java`
- `WeekendPricingStrategy.java`

**Demonstrates**:

- ✅ Strategy Pattern
- ✅ Polymorphism
- ✅ Runtime behavior selection

---

### 📦 `exception` (Custom Exceptions)

**Purpose**: Application-specific error handling

**Classes**:

- `ReservationException.java`
- `AuthenticationException.java`
- `DatabaseException.java`
- `ValidationException.java`

**Demonstrates**:

- ✅ Exception handling
- ✅ Inheritance (extends Exception)
- ✅ Error management

---

## Web Application Structure

### 📁 `src/main/webapp`

```
webapp/
├── index.jsp (Welcome page → redirects to login)
├── WEB-INF/
│   ├── web.xml (Deployment descriptor)
│   └── views/
│       ├── login.jsp
│       ├── dashboard.jsp
│       ├── add-reservation.jsp
│       ├── view-reservation.jsp
│       ├── update-reservation.jsp
│       ├── bill.jsp
│       ├── help.jsp
│       └── error.jsp
├── css/
│   └── custom.css (Additional styling if needed)
└── js/
    └── validation.js (Client-side validation)
```

**Technology Stack**:

- ✅ JSP (Server-side rendering)
- ✅ Tailwind CSS via CDN
- ✅ Font Awesome via CDN
- ✅ Vanilla JavaScript

---

## Database Structure

### 📁 `database/`

```
database/
├── schema.sql (Table creation)
├── data.sql (Initial data - room types, test users)
└── README.md (Database setup instructions)
```

**Tables**:

1. `users` - System users (staff)
2. `room_types` - Room categories and pricing
3. `reservations` - Guest bookings
4. `bills` - Payment records

---

## Test Structure

### 📁 `src/test/java`

```
test/java/lk/icbt/oceanview/oceanviewroomreservationsystem/
├── dao/
│   ├── UserDAOTest.java
│   ├── ReservationDAOTest.java
│   └── RoomTypeDAOTest.java
├── service/
│   ├── AuthenticationServiceTest.java
│   ├── ReservationServiceTest.java
│   └── BillingServiceTest.java
└── validation/
    ├── DateValidationTest.java
    └── InputValidationTest.java
```

**Testing Strategies**:

1. **Unit Testing** - Individual method testing
2. **Integration Testing** - DAO + Database interaction
3. **Validation Testing** - Boundary and edge cases

**Target**: 40+ test cases covering all validation scenarios

---

## Design Patterns Summary

| Pattern       | Location                 | Purpose                    |
|---------------|--------------------------|----------------------------|
| **MVC**       | Servlet + JSP + Model    | Separation of concerns     |
| **DAO**       | dao + dao.impl           | Data access abstraction    |
| **Singleton** | util.DatabaseConnection  | Single connection instance |
| **Factory**   | factory.DAOFactory       | Object creation            |
| **Strategy**  | strategy.PricingStrategy | Runtime pricing algorithms |

---

## OOP Principles Implementation

| Principle              | Implementation                   | Location               |
|------------------------|----------------------------------|------------------------|
| **Encapsulation**      | Private fields + getters/setters | All model classes      |
| **Inheritance**        | BaseEntity → User/Reservation    | model package          |
| **Polymorphism**       | Interface → Implementation       | DAO, Service layers    |
| **Abstraction**        | Interfaces, abstract classes     | DAO, Service, Strategy |
| **Method Overloading** | Multiple validate() methods      | Service layer          |
| **Method Overriding**  | doGet(), doPost()                | Servlet layer          |

---

## Technology Compliance

✅ **Pure Java + Jakarta EE**  
✅ **NO Spring/Spring Boot**  
✅ **NO Hibernate/JPA - Pure JDBC**  
✅ **NO Lombok**  
✅ **Maven for build management only**  
✅ **Tailwind CSS via CDN only**  
✅ **JUnit 5 for testing**

---

## Next Steps

1. ✅ **STEP 1 COMPLETED**: Project structure created
2. ⏳ **STEP 2**: Update pom.xml with MySQL connector and JSTL
3. ⏳ **STEP 3**: Create database schema
4. ⏳ **STEP 4**: Implement model classes (encapsulation demo)
5. ⏳ **STEP 5**: Implement DAO layer (DAO pattern, abstraction)
6. ⏳ **STEP 6**: Implement service layer (business logic)
7. ⏳ **STEP 7**: Implement utility classes (Singleton pattern)
8. ⏳ **STEP 8**: Implement servlets (MVC pattern)
9. ⏳ **STEP 9**: Create JSP views with Tailwind CSS
10. ⏳ **STEP 10**: Add JavaScript validation
11. ⏳ **STEP 11**: Implement JUnit tests
12. ⏳ **STEP 12**: Testing and deployment

---

## Academic Justification

This structure is designed to achieve **70-100 marks** by:

1. **Clear demonstration of ALL required OOP concepts**
2. **Proper implementation of ALL required design patterns**
3. **Strict 3-tier architecture with clear separation**
4. **Professional code organization and naming conventions**
5. **Comprehensive testing strategy**
6. **Full compliance with technology restrictions**
7. **Scalable and maintainable design**

---

**Author**: Generated for CIS6003 WRIT1 Assessment  
**Date**: January 2026  
**Version**: 1.0

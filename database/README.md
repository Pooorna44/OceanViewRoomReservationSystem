# Database Setup Guide

## Ocean View Resort - Database Configuration

---

## Database Information

**Database Name**: `oceanview_db`  
**Database Engine**: MySQL 8.0+  
**Character Set**: UTF-8 (utf8mb4)  
**Collation**: utf8mb4_unicode_ci

---

## Prerequisites

- MySQL Server 8.0 or higher installed
- MySQL Workbench or MySQL CLI
- Administrator access to MySQL

---

## Quick Setup (3 Steps)

### Step 1: Start MySQL Server

**Windows:**

```bash
# If MySQL is installed as a service
net start MySQL80
```

**Linux/Mac:**

```bash
sudo systemctl start mysql
# or
sudo service mysql start
```

### Step 2: Run Schema Script

**Option A: Using MySQL CLI**

```bash
mysql -u root -p < schema.sql
```

**Option B: Using MySQL Workbench**

1. Open MySQL Workbench
2. Connect to your MySQL server
3. File → Open SQL Script → Select `schema.sql`
4. Click Execute (⚡ icon)

### Step 3: Run Data Script

**Option A: Using MySQL CLI**

```bash
mysql -u root -p oceanview_db < data.sql
```

**Option B: Using MySQL Workbench**

1. In MySQL Workbench
2. File → Open SQL Script → Select `data.sql`
3. Click Execute (⚡ icon)

---

## Detailed Setup Instructions

### 1. Database Creation

The `schema.sql` script will:

- Drop existing `oceanview_db` database (if exists)
- Create new `oceanview_db` database
- Create 5 tables with proper constraints
- Create 2 views for common queries
- Create 2 stored procedures

### 2. Tables Created

| Table          | Purpose                     | Rows (Initial) |
|----------------|-----------------------------|----------------|
| `users`        | Staff login credentials     | 4 users        |
| `room_types`   | Room categories and pricing | 5 types        |
| `reservations` | Guest booking records       | 6 reservations |
| `bills`        | Billing information         | 2 bills        |
| `audit_log`    | System activity tracking    | 6 logs         |

### 3. Sample Data

The `data.sql` script provides:

- **4 Users**: 2 admins, 2 staff members
- **5 Room Types**: Standard, Deluxe, Suite, Family, Honeymoon
- **6 Sample Reservations**: Past, current, future, cancelled
- **2 Bills**: One paid, one pending
- **6 Audit Log Entries**: Login and activity tracking

---

## Database Schema Overview

### Entity Relationship Diagram (ASCII)

```
┌─────────────┐
│    users    │
├─────────────┤
│ user_id (PK)│
│ username    │
│ password    │
│ full_name   │
│ role        │
└─────┬───────┘
      │
      │ created_by
      │
┌─────▼──────────────┐         ┌──────────────┐
│   reservations     │────────>│  room_types  │
├────────────────────┤         ├──────────────┤
│ reservation_id (PK)│         │ room_type_id │
│ reservation_number │         │ type_name    │
│ guest_name         │         │ price_per_night│
│ room_type_id (FK)  │         │ capacity     │
│ check_in_date      │         └──────────────┘
│ check_out_date     │
│ status             │
└────────┬───────────┘
         │
         │ reservation_id
         │
    ┌────▼───────┐
    │   bills    │
    ├────────────┤
    │ bill_id    │
    │ total_amount│
    │ payment_status│
    └────────────┘
```

---

## Table Details

### 1. users

**Columns:**

- `user_id` (INT, PRIMARY KEY, AUTO_INCREMENT)
- `username` (VARCHAR(50), UNIQUE, NOT NULL)
- `password` (VARCHAR(255), NOT NULL) - SHA-256 hashed
- `full_name` (VARCHAR(100), NOT NULL)
- `email` (VARCHAR(100))
- `role` (VARCHAR(20)) - ADMIN, STAFF
- `is_active` (BOOLEAN, DEFAULT TRUE)
- `created_at`, `updated_at`, `last_login` (TIMESTAMP)

**Indexes:**

- idx_username, idx_role, idx_active

---

### 2. room_types

**Columns:**

- `room_type_id` (INT, PRIMARY KEY, AUTO_INCREMENT)
- `type_name` (VARCHAR(50), UNIQUE, NOT NULL)
- `description` (TEXT)
- `price_per_night` (DECIMAL(10,2), NOT NULL)
- `capacity` (INT, DEFAULT 2)
- `features` (TEXT)
- `is_available` (BOOLEAN, DEFAULT TRUE)
- `created_at`, `updated_at` (TIMESTAMP)

**Room Types & Pricing:**
| Type | Price (LKR) | Capacity |
|------|-------------|----------|
| Standard | 8,000 | 2 |
| Deluxe | 15,000 | 3 |
| Suite | 25,000 | 4 |
| Family Room | 18,000 | 5 |
| Honeymoon Suite | 30,000 | 2 |

---

### 3. reservations

**Columns:**

- `reservation_id` (INT, PRIMARY KEY, AUTO_INCREMENT)
- `reservation_number` (VARCHAR(50), UNIQUE, NOT NULL)
- `guest_name` (VARCHAR(100), NOT NULL)
- `address` (TEXT)
- `contact_number` (VARCHAR(20), NOT NULL)
- `email` (VARCHAR(100))
- `room_type_id` (INT, FOREIGN KEY → room_types)
- `check_in_date` (DATE, NOT NULL)
- `check_out_date` (DATE, NOT NULL)
- `number_of_guests` (INT, DEFAULT 1)
- `status` (VARCHAR(20)) - CONFIRMED, CANCELLED, COMPLETED, NO_SHOW
- `special_requests` (TEXT)
- `created_by` (INT, FOREIGN KEY → users)
- `created_at`, `updated_at` (TIMESTAMP)

**Constraints:**

- CHECK: `check_out_date > check_in_date`
- FOREIGN KEY: room_type_id, created_by

**Indexes:**

- Multiple indexes for performance (reservation_number, guest_name, dates, status)

---

### 4. bills

**Columns:**

- `bill_id` (INT, PRIMARY KEY, AUTO_INCREMENT)
- `reservation_id` (INT, UNIQUE, FOREIGN KEY → reservations)
- `reservation_number`, `guest_name`, `room_type` (VARCHAR)
- `check_in_date`, `check_out_date` (DATE)
- `number_of_nights` (INT, NOT NULL)
- `rate_per_night` (DECIMAL(10,2))
- `subtotal`, `tax_amount`, `discount_amount`, `total_amount` (DECIMAL(10,2))
- `payment_status` (VARCHAR(20)) - PENDING, PAID, CANCELLED
- `payment_method` (VARCHAR(50))
- `payment_date` (TIMESTAMP)
- `generated_by` (INT, FOREIGN KEY → users)
- `notes` (TEXT)

---

### 5. audit_log

**Columns:**

- `log_id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- `user_id` (INT, FOREIGN KEY → users)
- `action` (VARCHAR(50)) - LOGIN, LOGOUT, CREATE, UPDATE, DELETE, VIEW
- `table_name` (VARCHAR(50))
- `record_id` (INT)
- `description` (TEXT)
- `ip_address` (VARCHAR(45))
- `user_agent` (TEXT)
- `created_at` (TIMESTAMP)

---

## Views

### 1. v_active_reservations

Shows all confirmed reservations with room details and calculated totals.

**Query:**

```sql
SELECT * FROM v_active_reservations;
```

### 2. v_upcoming_checkins

Shows reservations checking in within the next 7 days.

**Query:**

```sql
SELECT * FROM v_upcoming_checkins;
```

---

## Stored Procedures

### 1. sp_generate_reservation_number

Generates unique reservation number in format: `RES-YYYYMMDD-XXXX`

**Usage:**

```sql
CALL sp_generate_reservation_number(@new_number);
SELECT @new_number;
```

### 2. sp_calculate_bill

Calculates bill details for a reservation.

**Usage:**

```sql
CALL sp_calculate_bill(1);  -- Pass reservation_id
```

---

## Test Login Credentials

### Default Users

| Username  | Password   | Role  | Description          |
|-----------|------------|-------|----------------------|
| `admin`   | `admin123` | ADMIN | System administrator |
| `staff1`  | `staff123` | STAFF | Staff member 1       |
| `staff2`  | `staff123` | STAFF | Staff member 2       |
| `manager` | `admin123` | ADMIN | Manager account      |

⚠️ **Security Note**:

- Passwords are stored as SHA-256 hashes in the database
- Change default passwords in production
- Consider using BCrypt for stronger security

---

## Verification Queries

### Check Database Creation

```sql
SHOW DATABASES LIKE 'oceanview_db';
```

### Check Tables

```sql
USE oceanview_db;
SHOW TABLES;
```

### Verify Data

```sql
-- Count records in each table
SELECT 'users' AS table_name, COUNT(*) AS record_count FROM users
UNION ALL
SELECT 'room_types', COUNT(*) FROM room_types
UNION ALL
SELECT 'reservations', COUNT(*) FROM reservations
UNION ALL
SELECT 'bills', COUNT(*) FROM bills
UNION ALL
SELECT 'audit_log', COUNT(*) FROM audit_log;
```

### Test Views

```sql
-- Active reservations
SELECT * FROM v_active_reservations;

-- Upcoming check-ins
SELECT * FROM v_upcoming_checkins;
```

---

## Connection Configuration for Java

Update the following in your `DatabaseConnection.java` utility class:

```java
private static final String URL = "jdbc:mysql://localhost:3306/oceanview_db";
private static final String USERNAME = "root";  // Change as needed
private static final String PASSWORD = "your_password";  // Your MySQL password
private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
```

### Connection String Parameters (Recommended)

```java
private static final String URL = 
    "jdbc:mysql://localhost:3306/oceanview_db" +
    "?useSSL=false" +
    "&serverTimezone=Asia/Colombo" +
    "&allowPublicKeyRetrieval=true";
```

---

## Troubleshooting

### Problem: Access Denied

**Solution:**

```sql
-- Grant privileges to user
GRANT ALL PRIVILEGES ON oceanview_db.* TO 'your_user'@'localhost';
FLUSH PRIVILEGES;
```

### Problem: Connection Refused

**Solution:**

- Check if MySQL server is running
- Verify port 3306 is not blocked
- Check firewall settings

### Problem: Character Encoding Issues

**Solution:**

```sql
-- Verify database charset
SELECT DEFAULT_CHARACTER_SET_NAME, DEFAULT_COLLATION_NAME
FROM INFORMATION_SCHEMA.SCHEMATA
WHERE SCHEMA_NAME = 'oceanview_db';
```

---

## Database Backup

### Create Backup

```bash
mysqldump -u root -p oceanview_db > oceanview_backup.sql
```

### Restore from Backup

```bash
mysql -u root -p oceanview_db < oceanview_backup.sql
```

---

## Performance Optimization

### Recommended Settings (my.cnf or my.ini)

```ini
[mysqld]
innodb_buffer_pool_size = 256M
max_connections = 100
query_cache_size = 32M
```

---

## Database Normalization

The schema follows **Third Normal Form (3NF)**:

- ✅ No repeating groups
- ✅ All non-key attributes depend on primary key
- ✅ No transitive dependencies
- ✅ Foreign key relationships properly defined

---

## Next Steps

After database setup:

1. ✅ Verify all tables created successfully
2. ✅ Test sample queries
3. ✅ Configure JDBC connection in Java
4. ⏳ Proceed to implement DAO layer
5. ⏳ Test database connectivity from Java application

---

## Support

For database-related issues:

1. Check MySQL error logs
2. Verify user permissions
3. Test connection using MySQL Workbench
4. Ensure correct MySQL version (8.0+)

---

**Database setup complete!** Ready for Java application integration. 🗄️

---

*Last Updated: January 12, 2026*

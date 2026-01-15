-- ============================================================================
-- Ocean View Resort - Room Reservation System Database Schema
-- ============================================================================
-- Course: CIS6003 Advanced Programming
-- Database: MySQL 8.0+
-- Created: January 12, 2026
-- ============================================================================

-- Drop existing database if exists (for clean setup)
DROP DATABASE IF EXISTS oceanview_db;

-- Create database
CREATE DATABASE oceanview_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Use the database
USE oceanview_db;

-- ============================================================================
-- Table: users
-- Purpose: Store staff login credentials for system access
-- ============================================================================
CREATE TABLE users
(
    user_id    INT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255)       NOT NULL COMMENT 'Hashed password (SHA-256 or BCrypt)',
    full_name  VARCHAR(100)       NOT NULL,
    email      VARCHAR(100),
    role       VARCHAR(20) DEFAULT 'STAFF' COMMENT 'ADMIN, STAFF',
    is_active  BOOLEAN     DEFAULT TRUE,
    created_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP          NULL,

    INDEX idx_username (username),
    INDEX idx_role (role),
    INDEX idx_active (is_active)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='System users - staff credentials';

-- ============================================================================
-- Table: room_types
-- Purpose: Store room categories and pricing information
-- ============================================================================
CREATE TABLE room_types
(
    room_type_id    INT PRIMARY KEY AUTO_INCREMENT,
    type_name       VARCHAR(50)    NOT NULL UNIQUE COMMENT 'Standard, Deluxe, Suite',
    description     TEXT,
    price_per_night DECIMAL(10, 2) NOT NULL CHECK (price_per_night > 0),
    capacity        INT       DEFAULT 2 CHECK (capacity > 0),
    features        TEXT COMMENT 'JSON or comma-separated features',
    is_available    BOOLEAN   DEFAULT TRUE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_type_name (type_name),
    INDEX idx_available (is_available)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='Room categories and pricing';

-- ============================================================================
-- Table: reservations
-- Purpose: Store guest booking records
-- ============================================================================
CREATE TABLE reservations
(
    reservation_id     INT PRIMARY KEY AUTO_INCREMENT,
    reservation_number VARCHAR(50) UNIQUE NOT NULL COMMENT 'Format: RES-YYYYMMDD-XXXX',
    guest_name         VARCHAR(100)       NOT NULL,
    address            TEXT,
    contact_number     VARCHAR(20)        NOT NULL,
    email              VARCHAR(100),
    room_type_id       INT                NOT NULL,
    check_in_date      DATE               NOT NULL,
    check_out_date     DATE               NOT NULL,
    number_of_guests   INT         DEFAULT 1 CHECK (number_of_guests > 0),
    status             VARCHAR(20) DEFAULT 'CONFIRMED' COMMENT 'CONFIRMED, CANCELLED, COMPLETED, NO_SHOW',
    special_requests   TEXT,
    created_by         INT COMMENT 'User who created the reservation',
    created_at         TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Foreign key constraints
    CONSTRAINT fk_reservation_room_type
        FOREIGN KEY (room_type_id)
            REFERENCES room_types (room_type_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,

    CONSTRAINT fk_reservation_created_by
        FOREIGN KEY (created_by)
            REFERENCES users (user_id)
            ON DELETE SET NULL
            ON UPDATE CASCADE,

    -- Business rule: check-out must be after check-in
    CONSTRAINT chk_dates
        CHECK (check_out_date > check_in_date),

    -- Indexes for performance
    INDEX idx_reservation_number (reservation_number),
    INDEX idx_guest_name (guest_name),
    INDEX idx_contact_number (contact_number),
    INDEX idx_check_in_date (check_in_date),
    INDEX idx_check_out_date (check_out_date),
    INDEX idx_status (status),
    INDEX idx_room_type (room_type_id),
    INDEX idx_date_range (check_in_date, check_out_date)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='Guest booking records';

-- ============================================================================
-- Table: bills (Optional - for record keeping)
-- Purpose: Store billing information for reservations
-- ============================================================================
CREATE TABLE bills
(
    bill_id            INT PRIMARY KEY AUTO_INCREMENT,
    reservation_id     INT            NOT NULL UNIQUE,
    reservation_number VARCHAR(50)    NOT NULL,
    guest_name         VARCHAR(100)   NOT NULL,
    room_type          VARCHAR(50)    NOT NULL,
    check_in_date      DATE           NOT NULL,
    check_out_date     DATE           NOT NULL,
    number_of_nights   INT            NOT NULL CHECK (number_of_nights > 0),
    rate_per_night     DECIMAL(10, 2) NOT NULL CHECK (rate_per_night > 0),
    subtotal           DECIMAL(10, 2) NOT NULL CHECK (subtotal >= 0),
    tax_amount         DECIMAL(10, 2) DEFAULT 0 CHECK (tax_amount >= 0),
    discount_amount    DECIMAL(10, 2) DEFAULT 0 CHECK (discount_amount >= 0),
    total_amount       DECIMAL(10, 2) NOT NULL CHECK (total_amount >= 0),
    payment_status     VARCHAR(20)    DEFAULT 'PENDING' COMMENT 'PENDING, PAID, CANCELLED',
    payment_method     VARCHAR(50) COMMENT 'CASH, CARD, BANK_TRANSFER',
    payment_date       TIMESTAMP      NULL,
    generated_by       INT COMMENT 'User who generated the bill',
    generated_at       TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    notes              TEXT,

    -- Foreign key constraints
    CONSTRAINT fk_bill_reservation
        FOREIGN KEY (reservation_id)
            REFERENCES reservations (reservation_id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,

    CONSTRAINT fk_bill_generated_by
        FOREIGN KEY (generated_by)
            REFERENCES users (user_id)
            ON DELETE SET NULL
            ON UPDATE CASCADE,

    -- Indexes
    INDEX idx_reservation_id (reservation_id),
    INDEX idx_reservation_number (reservation_number),
    INDEX idx_payment_status (payment_status),
    INDEX idx_generated_at (generated_at)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='Billing records';

-- ============================================================================
-- Table: audit_log (Optional - for security tracking)
-- Purpose: Track all system activities for security and debugging
-- ============================================================================
CREATE TABLE audit_log
(
    log_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id     INT,
    action      VARCHAR(50) NOT NULL COMMENT 'LOGIN, LOGOUT, CREATE, UPDATE, DELETE, VIEW',
    table_name  VARCHAR(50),
    record_id   INT,
    description TEXT,
    ip_address  VARCHAR(45),
    user_agent  TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_audit_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE SET NULL
            ON UPDATE CASCADE,

    INDEX idx_user_id (user_id),
    INDEX idx_action (action),
    INDEX idx_table_name (table_name),
    INDEX idx_created_at (created_at)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='System audit trail';

-- ============================================================================
-- Create Views for Common Queries
-- ============================================================================

-- View: Active Reservations with Room Details
CREATE VIEW v_active_reservations AS
SELECT r.reservation_id,
       r.reservation_number,
       r.guest_name,
       r.contact_number,
       r.check_in_date,
       r.check_out_date,
       DATEDIFF(r.check_out_date, r.check_in_date)                      AS number_of_nights,
       rt.type_name                                                     AS room_type,
       rt.price_per_night,
       DATEDIFF(r.check_out_date, r.check_in_date) * rt.price_per_night AS estimated_total,
       r.status,
       r.created_at
FROM reservations r
         JOIN room_types rt ON r.room_type_id = rt.room_type_id
WHERE r.status = 'CONFIRMED'
ORDER BY r.check_in_date;

-- View: Upcoming Check-ins (Next 7 Days)
CREATE VIEW v_upcoming_checkins AS
SELECT r.reservation_number,
       r.guest_name,
       r.contact_number,
       r.check_in_date,
       rt.type_name                         AS room_type,
       DATEDIFF(r.check_in_date, CURDATE()) AS days_until_checkin
FROM reservations r
         JOIN room_types rt ON r.room_type_id = rt.room_type_id
WHERE r.check_in_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)
  AND r.status = 'CONFIRMED'
ORDER BY r.check_in_date;

-- ============================================================================
-- Create Stored Procedures (Optional - for complex operations)
-- ============================================================================

DELIMITER //

-- Procedure: Generate unique reservation number
CREATE PROCEDURE sp_generate_reservation_number(OUT new_number VARCHAR(50))
BEGIN
    DECLARE date_string VARCHAR(8);
    DECLARE sequence INT;

    -- Get current date in YYYYMMDD format
    SET date_string = DATE_FORMAT(CURDATE(), '%Y%m%d');

    -- Get next sequence number for today
    SELECT COALESCE(MAX(CAST(SUBSTRING(reservation_number, 14) AS UNSIGNED)), 0) + 1
    INTO sequence
    FROM reservations
    WHERE reservation_number LIKE CONCAT('RES-', date_string, '%') COLLATE utf8mb4_unicode_ci;

    -- Generate reservation number: RES-YYYYMMDD-XXXX
    SET new_number = CONCAT('RES-', date_string, '-', LPAD(sequence, 4, '0'));
END //

-- Procedure: Calculate bill for a reservation
CREATE PROCEDURE sp_calculate_bill(IN p_reservation_id INT)
BEGIN
    SELECT r.reservation_id,
           r.reservation_number,
           r.guest_name,
           rt.type_name                                                     AS room_type,
           r.check_in_date,
           r.check_out_date,
           DATEDIFF(r.check_out_date, r.check_in_date)                      AS number_of_nights,
           rt.price_per_night,
           DATEDIFF(r.check_out_date, r.check_in_date) * rt.price_per_night AS total_amount
    FROM reservations r
             JOIN room_types rt ON r.room_type_id = rt.room_type_id
    WHERE r.reservation_id = p_reservation_id;
END //

DELIMITER ;

-- ============================================================================
-- Database Schema Statistics
-- ============================================================================
-- Tables Created: 5 (users, room_types, reservations, bills, audit_log)
-- Views Created: 2 (active_reservations, upcoming_checkins)
-- Stored Procedures: 2 (generate_reservation_number, calculate_bill)
-- ============================================================================

-- Display success message
SELECT 'Database schema created successfully!' AS status;
SELECT 'Next step: Run data.sql to populate initial data' AS next_action;
